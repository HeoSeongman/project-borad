// div 로 답글 요소 구분
let replyDiv = document.createElement('div');
replyDiv.setAttribute("class", "replyContainer")

// text 요소 추가
let replyTextarea = document.createElement('textarea');
replyTextarea.setAttribute("class", "inputTextarea form-control");
replyTextarea.setAttribute("name", "content");

// 답글 버튼 요소 구분
let replyButtonsDiv = document.createElement('div');
replyButtonsDiv.setAttribute("class", "postButtonsBottom");

// 답글 취소 버튼
let replyCancel = document.createElement('a');
replyCancel.setAttribute("class", "btn btn-primary");
replyCancel.textContent = "취소";

// 답글 제출 버튼
let replySubmit = document.createElement('button');
replySubmit.setAttribute("class", "btn btn-outline-primary");
replySubmit.setAttribute("type", "button");
replySubmit.textContent = "쓰기";

// 답글 요소들 조립
replyButtonsDiv.appendChild(replyCancel);
replyButtonsDiv.appendChild(replySubmit);
replyDiv.appendChild(replyTextarea);
replyDiv.appendChild(replyButtonsDiv);


// 열거형 선언
const ButtonType = {
    CreateComment: 0,
    CreateReply: 1,
    UpdateComment: 2,
    UpdateReply: 3,
    DeleteComment: 4,
    DeleteReply: 5
};
// 열거형 불변화
Object.freeze(ButtonType);


// 요청 데이터
let requestData = {
    requestButtonType: -1,
    requestURL: "",
    requestBody: {
    }
}

// 추가할 댓글,답글 생성 함수
function createAddElement(responseData) {
    // 추가될 댓글 요소 구분
    let add_ul_Element = document.createElement("ul");

    let add_li_Element = document.createElement("li");
    add_li_Element.setAttribute("class", "");
    add_li_Element.setAttribute("id", responseData.id);

    let add_createdBy = document.createElement("p");
    add_createdBy.textContent = responseData.createdBy;

    let add_content = document.createElement("p");
    add_content.setAttribute("class", "CommentReplyContent");
    add_content.textContent = responseData.content;

    let add_createdAt = document.createElement("p");
    add_createdAt.setAttribute("class", "CreatedAt");
    add_createdAt.textContent = responseData.createdAt;

    let add_replyButton = document.createElement("a");
    add_replyButton.setAttribute("href", "javascript:void(0)");
    add_replyButton.setAttribute("class", "replyButton edit-button");
    add_replyButton.setAttribute("value", "1");
    add_replyButton.textContent = "답글쓰기"
    editButtonEventRegister(add_replyButton);

    add_li_Element.appendChild(add_createdBy);
    add_li_Element.appendChild(add_content);
    add_li_Element.appendChild(add_createdAt);
    add_li_Element.appendChild(add_replyButton);

    if (requestData.requestButtonType === ButtonType.CreateComment) {
        add_li_Element.setAttribute("class", "CommentItem border-bottom");
        add_createdBy.setAttribute("class", "commentCreatedBy");

        add_ul_Element.appendChild(add_li_Element);
        return add_ul_Element;

    } else {
        add_li_Element.setAttribute("class", "CommentItem ReplyItem border-bottom");

        return add_li_Element;
    }
}

// 정상 응답 시 행동
function doAfterResponse(responseData) {
    let createdElement = null;
    switch(requestData.requestButtonType) {
        case ButtonType.CreateComment:
            createdElement = createAddElement(responseData);
            document.getElementsByClassName("blog-post-comments")[0].appendChild(createdElement);
            document.getElementById("articleComment").value = "";
            break;
        case ButtonType.CreateReply:
            createdElement = createAddElement(responseData);
            replyDiv.parentNode.appendChild(createdElement);
            closeTextarea();
            break;
        case ButtonType.UpdateComment:
            replyDiv.nextElementSibling.textContent = responseData.content;
            closeTextarea();
            break;
        case ButtonType.UpdateReply:
            replyDiv.nextElementSibling.textContent = responseData.content;
            closeTextarea();
            break;
    }
}

// 요청 데이터 설정
function setRequestData(buttonMyself) {
    switch(requestData.requestButtonType) {
        case ButtonType.CreateComment:
            requestData.requestURL = "/createCommentJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].getAttribute("value"),
                content: document.getElementById("articleComment").value
            };
            break;
        case ButtonType.CreateReply:
            requestData.requestURL = "/createReplyJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].getAttribute("value"),
                articleCommentId: replyDiv.parentNode.firstElementChild.getAttribute("id"),
                content: replyTextarea.value
            };
            break;
        case ButtonType.UpdateComment:
            requestData.requestURL = "/" + replyDiv.parentNode.getAttribute("id") + "/updateCommentJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].value,
                content: replyTextarea.value
            };
            break;
        case ButtonType.UpdateReply:
            requestData.requestURL = "/" + replyDiv.parentNode.getAttribute("id") + "/updateReplyJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].value,
                articleCommentId: replyDiv.parentNode.parentNode.firstElementChild.getAttribute("id"),
                content: replyTextarea.value
            };
            break;
        case ButtonType.DeleteComment:
            requestData.requestURL = "/deleteCommentJSON";
//            requestData.requestBody = {
//                            commentId: buttonMyself.parentNode.parentNode.getAttribute("id")
//                        };
// commentId 만 매핑할려고 했으나 객체형태로 보내면
// JSONObject to Long 매핑 오류가 났다.
// 하나 보낼거면 키값 형태가 아니라 값만 보내면 정상 매핑해준다.
            requestData.requestBody = buttonMyself.parentNode.parentNode.getAttribute("id");
            break;
        case ButtonType.DeleteReply:
            requestData.requestURL = "/deleteReplyJSON";
            requestData.requestBody = buttonMyself.parentNode.parentNode.getAttribute("id");
            break;
    }
    console.log(requestData);
}


// 취소시 답글 요소 삭제
replyCancel.addEventListener('click', (myself) => {
    closeTextarea();
});

// 답글 입력 요소 삭제
function closeTextarea() {

    if (replyDiv.nextElementSibling != null) {
        replyDiv.nextElementSibling.removeAttribute("hidden");
    }
    if (replyDiv.parentElement != null) {
        replyDiv.parentNode.removeChild(replyDiv);
        replyTextarea.value = "";
    }
}


// 수정, 댓글쓰기 버튼들 클릭 이벤트 등록
function editButtonEventRegister(button) {
    button.addEventListener('click', async (myself) => {

        requestData.requestButtonType = Number(myself.target.getAttribute('value'));

        switch(requestData.requestButtonType) {
            case ButtonType.CreateComment:
                break;
            case ButtonType.CreateReply:
                myself.target.parentNode.parentNode.insertBefore(replyDiv, myself.target.parentNode.nextElementSibling);
                break;
            case ButtonType.UpdateComment:
                myself.target.parentNode.parentNode.insertBefore(
                        replyDiv, myself.target.parentNode.parentNode.getElementsByClassName("CommentReplyContent")[0]);
                replyTextarea.value = replyDiv.parentNode.getElementsByClassName("CommentReplyContent")[0].textContent;
                replyDiv.nextElementSibling.setAttribute("hidden", "hidden");
                break;
            case ButtonType.UpdateReply:
                myself.target.parentNode.parentNode.insertBefore(
                        replyDiv, myself.target.parentNode.parentNode.getElementsByClassName("CommentReplyContent")[0]);
                replyTextarea.value = replyDiv.parentNode.getElementsByClassName("CommentReplyContent")[0].textContent;
                replyDiv.nextElementSibling.setAttribute("hidden", "hidden");
                break;
        };
    });
}

[...document.getElementsByClassName('edit-button')].forEach( (editButton) => {
    editButtonEventRegister(editButton);
});


// 제출 버튼들 클릭 이벤트 등록
[document.getElementById("submitButton"), replySubmit].forEach( (button) => {
    button.addEventListener('click', async (myself) => {

       setRequestData(myself.target);
       if (requestData.requestBody.content.trim().length === 0) {
           alert("올바르게 입력하세요.");
           return;
       }
       let responseData = await postData(requestData.requestURL, requestData.requestBody);
       console.log(responseData);

       doAfterResponse(responseData);
   });
});


// 삭제 버튼들 클릭 이벤트 등록
[...document.getElementsByClassName("deleteButton")].forEach( (button) => {
    button.addEventListener('click', async (myself) => {

        setRequestData(myself.target);
        if (!confirm("삭제하시겠습니까?")) {
            return;
        }
        let responseData = await postData(requestData.requestURL, requestData.requestBody);
        console.log(responseData);

        doAfterResponse(responseData);
    });
});

let _csrf = document.getElementById("_csrf").content;
let _csrf_header = document.getElementById("_csrf_header").content;

// 데이터 제출
async function postData(sendURL, bodyData) {
    let promiseData = await fetch("/comments" + sendURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': _csrf
        },
        body: JSON.stringify(bodyData),
    });
    return promiseData.json();
//    .then( (response) => console.log(response.json()) );
}