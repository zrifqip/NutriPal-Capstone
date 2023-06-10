const db  = require('./dbConfig');
const express = require('express');
const router = express.Router();


const { signupValidation, loginValidation } = require('./validation');
const { validationResult } = require('express-validator');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

router.post('/signup', (req, res,) => {
    db.query(`SELECT * FROM users WHERE LOWER(email) = LOWER(${db.escape(req.body.email)});`, (err, result) => {
        if (req.body.confirmationPassword !== req.body.password) {
            return res.status(409).send({msg: 'Passwords do not match!'});
        } else {
            // Confirmation password matches the intended password
            bcrypt.hash(req.body.password, 10, (err, hash) => {
                if (err) {
                    return res.status(500).send({msg: err});
                } else {
                    // Password has been hashed
                    db.query(`INSERT INTO users (name, email, password) VALUES ('${req.body.name}', ${db.escape(req.body.email)}, ${db.escape(hash)});`,(err, result) => {
                        if (err) {
                            throw err;
                            return res.status(400).send({msg: err});
                        }
                        return res.status(201).json({error: false, message: "User Created"});
                    });
                }
            });
        }
    });
});

router.post('/login', (req, res) => {
    db.query(`SELECT * FROM users WHERE email = ${db.escape(req.body.email)};`,(err, result) => {
        // User does not exists (email is not registered yet)
        if (err) {
            throw err;
            return res.status(400).send({msg: err});
        }
        if (!result.length) {
            return res.status(401).send({msg: 'Incorrect username or password!!!'});
        }
        // Verify password
        let isVerified = bcrypt.compare(req.body.password, result[0]['password']);
        if (!isVerified) {
            //Password doesn't match
          return res.status(401).send({msg: 'Incorrect username or password!'});
        }
        const token = jwt.sign({id:result[0].id_user},'my-32-character-ultra-secure-and-ultra-long-secret',{ expiresIn: '365d' });
        db.query(`UPDATE users SET last_login = now(), fill_survey = true WHERE id_user = '${result[0].id_user}';`);
        return res.status(200).send({error: false, msg: 'success', token, loginResult: result[0]});
      });
    });

module.exports = router;