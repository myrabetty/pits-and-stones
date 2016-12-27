
var game = {};

var stompClient = null;

/**
 * it creates a Websocket with a specified end-point.
 * It subscribe to the destination client destination
 * (From now on anything pushed through this channel will reach the client)
 * It call the controller to set the client in the queue
 *
 */
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/receive-update-client/' + $('#httpSessionId').val(), function (message) {
            var gameUpdate = JSON.parse(message.body);
            updateView(gameUpdate);
        });
        login();
    });
}


/**
 * send the client details to the game-login channel so that it can be added to the queue
 *
 */
function login() {
    stompClient.send(
            "/app/game-login",
            {},
            JSON.stringify({
                'sessionId': $('#httpSessionId').val()
            })
            );
}

/** 
 * create webSocket and init the board in the correct state.
 * 
 * @param {type} param
 */
$(document).ready(function () {
    /* WebSocket*/
    connect();

    /*old fashion controller*/
    /*var init = $.ajax({
     method: "GET",
     url: "/init",
     dataType: "json"
     });
     
     init.then(function (response) {
     game = response;
     console.log(game.board);
     setPitsValues(game.board);
     });*/
});

/** will submit the board through the webSocket
 * 
 * @param {type} i
 * @returns {undefined}
 */
function submitBoard(i) {
    game.selectedPit = i;

    /*old fashion controller*/
    //var selected = source.getAttribute('id');
    //$(#selectedField").val(i);
    /*var input = JSON.stringify(game);
     var submit = $.ajax({
     method: "POST",
     url: "/",
     data: input,
     contentType: "application/json",
     dataType: "json"
     });
     submit.then(update);*/

    /* webSocket*/
    stompClient.send(
            "/app/perform-move-server",
            {},
            JSON.stringify(game)
            );
}

/**updates the view of the board
 * 
 */
function updateView(response) {
    game = response;
    setPitsValues(game.board);
    if (game.activePlayer !== null && typeof game.activePlayer !== undefined) {
        $("#waiting-message").attr("style", "display:none");
        $("#ongoing-message").removeAttr("style", "display:none");
    }
    if (game.over) {
        $("#ongoing-message").attr("style", "display:none");
        $("#game-over-message").removeAttr("style", "display:none");
    }
    $("#player2BigPit").val(game.board.bigPitPlayer2.numberOfStones);
    $("#player1BigPit").val(game.board.bigPitPlayer1.numberOfStones);
}

/**
 * updates the state of the small pits (number of stones, and active state)
 *
 */
function setPitsValues(board) {
    for (var i = 0; i < 6; i++) {
        $("#player2_pit" + i).val(board.pitsPlayer2[i].numberOfStones).removeAttr("disabled");
        if (board.pitsPlayer2[i].active === false) {
            $("#player2_pit" + i).attr("disabled", "disabled");
        }
        $("#player1_pit" + i).val(board.pitsPlayer1[i].numberOfStones).removeAttr("disabled");
        if (board.pitsPlayer1[i].active === false) {
            $("#player1_pit" + i).attr("disabled", "disabled");
        }
    }
}
