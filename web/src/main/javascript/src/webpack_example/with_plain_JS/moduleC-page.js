/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

var moduleB = require('./moduleB.js');

// This is the evil way:
//module.exports = function (modB) {
//
//    function init() {
//        return 'Hola mundo desde C; el module B dice "' + modB.init() + '"';
//    }
//
//    return {
//        init: init
//    }
//
//}(moduleB);

// This is the Webpack good way:
exports.init = function() {
    return 'Hola mundo desde C; el module B dice "' + moduleB.init() + '"';
};
