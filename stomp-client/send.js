var Stomp = require('stompjs')
var SockJS = require('sockjs-client')
var url = 'http://localhost:8080/moves';
console.log('connecting to '+url);

var sock= new SockJS(url);
var stompClient = Stomp.over(sock);


var callback = function(message){
  console.log('received message: '+message);
};


sock.onopen = function() {
  console.log('open');

};

sock.onmessage = function(e) {
  console.log('message', e.data);
  sock.close();
};

sock.onclose = function() {
  console.log('close');
};


stompClient.connect({},function (frame) {
    console.log("connected");
    stompClient.send('/move',{},'{"position":"p11","boardName":"jean"}');
});