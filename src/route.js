const db  = require('./dbConfig');
const express = require('express');
const router = express.Router();

const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

router.post('/signup', (req, res,) => {
    const query1 = "SELECT * FROM user WHERE LOWER(email) = LOWER(?)"
    db.query(query1, [req.body.email], (err, result) => {
        if (err) {
            return res.status(400).send({ msg: err });
        }

        if (result.length) {
            return res.status(409).send({
                msg: 'This email is already in use!'
            });
        } else {
            //Check if passwords match
            if (req.body.confirmationPassword !== req.body.password) {
                return res.status(409).send({ msg: 'Passwords do not match!' });
            } else {
                console.log("Checkpoint CEK PASSWORD SAMA GA");
                // Confirmation password matches the intended password
                bcrypt.hash(req.body.password, 10, (err, hash) => {
                    if (err) {
                        return res.status(500).send({ msg: err });
                    } else {
                        console.log("Checkpoint HASH OK");
                        // Password has been hashed
                        const query2 = "INSERT INTO user (name, email, password) values (?, ?, ?)"
                        db.query(query2, [(req.body.name), (req.body.email), hash], (err, result) => {
                            if (err) {
                                //throw err;
                                return res.status(400).send({ msg: err });
                            }
                            return res.status(201).json({ error: false, message: "User Created" });
                        });
                    }
                });
            }
        }
    });
});

router.post('/login', (req, res) => {
    db.query(`SELECT * FROM user WHERE email = ${db.escape(req.body.email)};`,(err, result) => {
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
        const token = jwt.sign({id:result[0].id_user},process.env.SECRET_KEY,{ expiresIn: '365d' });
        db.query(`UPDATE user SET last_login = now(), fill_survey = true WHERE id_user = '${result[0].id_user}';`);
        //return res.status(200).send({error: false, msg: 'success', token, loginResult: result[0]});
        return res.status(200).send({
            error: false, 
            msg: 'success', 
            loginResult: {
                id_user: result[0].id_user,
                name: result[0].name,
                email: result[0].email,
                survey: result[0].fill_survey,
                lastLogin: result[0].last_login,
                token: token
            }
        });
      });
    });

module.exports = router;