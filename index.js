const express = require('express');
const app = express();
const cors = require('cors');
const bodyParser = require('body-parser');
const apiRouter = require('./src/route'); 
 
app.use(express.json()); 
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());
 
app.use('/auth', apiRouter);

app.get("/", (req, res) => {
    console.log("OK")
    res.send("Response Success!")
});
// Handling Errors
/*app.use((err, req, res, next) => {
    // console.log(err);
    err.statusCode = err.statusCode || 500;
    err.message = err.message || "Internal Server Error";
    res.status(err.statusCode).json({message: err.message});
});*/
 
const PORT = process.env.PORT || 8080
app.listen(PORT, () => {
    console.log("Server is up and listening on port " + PORT)
})