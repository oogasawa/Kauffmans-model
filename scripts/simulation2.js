#!/usr/bin/env node

var progInfo = {
    "homeDir": process.env.HOME,
    "projectDir": "/works/Kauffmans-model",
    "jarName": "Kauffmans-model-1.0-jar-with-dependencies.jar",
    "app": "net.ogalab.kauffman.Simulator2",
    "parameters": {
        "number-of-elements": 1000,
        "number-of-trials": 10000,
        "interval": 1000
    }
};

progInfo.makeJarFullPath = function makeJarFullPath() {
    var jar = progInfo.homeDir
    jar += progInfo.projectDir
    jar += "/target/" + progInfo.jarName

    return jar
}

progInfo.makeCommand = function makeCommand() {
    var com = "java -cp " + progInfo.makeJarFullPath() + " " + progInfo.app
    for (var k in progInfo.parameters) {
        com += " --" + k + "=" + progInfo.parameters[k]
    }

    return com
}


//console.log(progInfo.makeJarFullPath())
//console.log(progInfo.makeCommand())

var com = progInfo.makeCommand()

const exec = require('child_process').exec;
exec(com, (err, stdout, stderr) => {
  if (err) { console.log(err); }
  console.log(stdout);
});

