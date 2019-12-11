var Stomp = require('stompjs')
var SockJS = require('sockjs-client')
var url = 'http://localhost:8080/ws';
console.log('connecting to '+url);

var sock= new SockJS(url);
var stompClient = Stomp.over(sock);


var callback = function(message){
  console.log('received message: '+message);
};


stompClient.connect({},function (frame) {
    stompClient.subscribe('/topic/moves', callback);
});