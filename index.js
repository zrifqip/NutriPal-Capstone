const cors = require('cors');
const express = require('express');
const bodyParser = require('body-parser');
const mainRouter = require('./src/route');

const app = express();

//app.use(bodyParser.json());
app.use(express.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use('/', mainRouter);
app.use(cors());

const PORT = process.env.PORT || 8080
app.listen(PORT, () => {
    console.log("Server is up and listening on " + PORT)
})