var k = function() {
    // val()은 엘리먼트가 가지고 있는 value 속성의 값을 가져온다.
    var 메세지 = $('#chat-room .input-box #text-input').val();
    
    // 메세지 변수에 들어있는 값이 ''와 같다면
    if ( 메세지 == '' ) {
        // 함수를 더 이상 진행시키지 않고 여기서 종료시킨다.
        return false;
    }
    
    var html = `
    <div class="chat-message mine">
        <section><i class="fa fa-user"></i></section>
        <span>홍길동</span>
        <div>${메세지}</div>
    </div>
    `;
    
    $('#chat-room .message-group:last-child').append(html);
    
    // val(문자열)은 엘리먼트가 가지고 있는 value 속성의 값을 입력받은 문자열로 교체하라는 뜻입니다.
    $('#chat-room .input-box #text-input').val('');
};

$('#chat-room .input-box .btn-submit').click(k);

// input 창에서 키보드 눌림 이벤트 발생시 함수를 실행하도록 예약
$('#chat-room .input-box #text-input').keydown(function(e) {
    // 만약 입력한 키코드가 13, 즉 엔터라면 함수를 실행한다.
    if ( e.keyCode == 13 ) {
        k();
    }
});