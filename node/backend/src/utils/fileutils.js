module.exports = function(){

  const fs = require('fs');
  const path = require('path');

  function  readFiles(dirname) {
    var files = fs.readdirSync(dirname);
    var f = {};
    f.files = [];
    f.dirs = [];
    f.dirname = dirname;

    files.forEach( file => {
      var fullpath = path.join(dirname, file);
      if(fs.statSync(fullpath).isDirectory()){
        f.dirs.push(readFiles(fullpath));
      }else{
        f.files.push(file);
      }

    });


    return f;
  }

  this.getFiles = function(path){
    return readFiles(path);
  }


}
