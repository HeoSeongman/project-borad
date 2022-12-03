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
replySubmit.setAttribute("class", "btn btn-outline-primary submitButton");
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
    UpdateReply: 3
};
// 열거형 불변화
Object.freeze(ButtonType);


//let add-form-Element = document.createElement("form");
//add-form-Element.setAttribute("class", "postButtonsRight");
//add-form-Element.setAttribute("action", "/");
//add-form-Element.setAttribute("method", "post");





let requestData = {
    requestButtonType: -1,
    requestURL: "",
    requestBody: {
    }
}

function createAddElement(responseData) {
    // 추가될 댓글 요소 구분
    let add_ul_Element = document.createElement("ul");

    let add_li_Element = document.createElement("li");

    let add_createdBy = document.createElement("p");
    add_createdBy.setAttribute("class", "commentCreatedBy");

    let add_content = document.createElement("p");
    add_content.setAttribute("class", "CommentReplyContent");

    let add_createdAt = document.createElement("p");
    add_createdAt.setAttribute("class", "CreatedAt");

    let add_replyButton = document.createElement("a");
    add_replyButton.setAttribute("href", "javascript:void(0)");
    add_replyButton.setAttribute("class", "replyButton edit-button");
    add_replyButton.setAttribute("value", "-1");

    add_li_Element.appendChild(add_createdBy);
    add_li_Element.appendChild(add_content);
    add_li_Element.appendChild(add_createdAt);
    add_li_Element.appendChild(add_replyButton);

    if (requestData.requestButtonType === ButtonType.CreateComment) {
        add_li_Element.setAttribute("class", "CommentItem border-bottom");
        add_li_Element.setAttribute("id", responseData.id);

        add_createdBy.textContent = responseData.createdBy;
        add_content.textContent = responseData.content;
        add_createdAt.textContent = responseData.createdAt;

        add_ul_Element.appendChild(add_li_Element);
        return add_ul_Element;
    } else {
        return add_li_Element;
    }
}


function doAfterResponse(responseData) {
    switch(requestData.requestButtonType) {
        case ButtonType.CreateComment:
            let createdElement = createAddElement(responseData);
            document.getElementsByClassName("blog-post-comments")[0].appendChild(createdElement);
            document.getElementById("articleComment").value = "";
    }
}


function setRequestData() {
    switch(requestData.requestButtonType) {
        case ButtonType.CreateComment:
            requestData.requestURL = "/createCommentJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].value,
                content: document.getElementById("articleComment").value
            };
            break;
        case ButtonType.CreateReply:
            requestData.requestURL = "/createReplyJSON";
            requestData.requestBody = {
                articleId: document.getElementsByName('articleId')[0].value,
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
    }
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


// 버튼들 가져오기
let editButtons = document.getElementsByClassName('edit-button');

// 답글쓰기, 수정버튼 요소에 이벤트 추가
for (let i = 0; i < editButtons.length; i++) {
//    console.log(replyButtons[i]);
    editButtons[i].addEventListener('click', async (myself) => {

        if (replyDiv.parentNode != null) {
            closeTextarea();
        }

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
    })
}

let submitButtons = document.getElementsByClassName("submitButton");

for (let i = 0; i < submitButtons.length; i++) {
    // 답글 제출 버튼 클릭 시 수정된 데이터 제출
    submitButtons[i].addEventListener('click', async (myself) => {

        setRequestData();
        let responseData = await postData(requestData.requestURL, requestData.requestBody);
        console.log(responseData);

//        replyDiv.parentNode.getElementsByClassName("CommentReplyContent")[0].textContent = responseData.content;
//        closeTextarea();

        if (requestData.requestButtonType === ButtonType.CreateComment) {
            document.getElementById("articleComment").value = "";
        } else {
            closeTextarea();
        }

        doAfterResponse(responseData);
    })
}


// 데이터 제출
async function postData(sendURL, bodyData) {
    let promiseData = await fetch("/comments" + sendURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "X-CSRF-Token": document.getElementsByName("_csrf")[0].value
        },
        body: JSON.stringify(bodyData),
    });
    return promiseData.json();
//    .then( (response) => console.log(response.json()) );
}