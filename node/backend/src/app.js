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
//CORS
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');

    // authorized headers for preflight requests
    // https://developer.mozilla.org/en-US/docs/Glossary/preflight_request
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    next();

    app.options('*', (req, res) => {
        // allowed XHR methods
        res.header('Access-Control-Allow-Methods', 'GET, PATCH, PUT, POST, DELETE, OPTIONS');
        res.send();
    });
});


app.route(c.REST_PATH + 'config/server')
  .get(function(req,res){
    console.log('petition received');
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
