const mysql = require('mysql');
//TO DO: Replace it with your current database
const db = mysql.createConnection({
  host: 'localhost', 
  user: 'root',      
  password: '',      
  database: 'usercapstone' 
}); 
 
db.connect(function(err) {
  if (err) throw err;
  console.log('Database is connected successfully !');
});
module.exports = db;