const jwt = require('jsonwebtoken');

const authorization = (req, res, next) => {
    const token = req.headers.authorization;
    if (!token) {
      return res.status(401).send("AUTHORIZATION ERROR!!!");
    }
    try {
      let payload = jwt.verify(token, process.env.SECRET_KEY);
      console.log(payload);
      return next();
    } 
    catch {
      return res.status(401).send("AUTHORIZATION FAILED!!!");
    }
  };

module.exports = authorization;