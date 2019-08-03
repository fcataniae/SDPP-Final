const Express = require('express');
const bodyparser = require('body-parser');
const querystring = require('querystring');

const app = Express();
const fs = require('fs');
const multer  = require('multer');
const __DIR = 'uploads/';
const upload = multer({ dest: __DIR });
const Consts = require('./const/consts.js');

const c = new Consts();

const config = require(c.CONFIG_FILE);


app.use(bodyparser.urlencoded({ extended: false }));
app.use(bodyparser.json());

app.route(c.REST_PATH + 'config/server')
  .get(function(req,res){
    res.send(config);
  });

app.use(function(req, res, next) {
 respuesta = {
  error: true,
  codigo: 404,
  mensaje: 'URL no encontrada'
 };
 res.status(404).send(respuesta);
});

const port = 17752;
app.listen(port, () => {
 console.log('El servidor está inicializado en el puerto %d',port);
});
