// div 로 답글 요소 구분
let replyDiv = document.createElement('div');
replyDiv.setAttribute("class", "replyWriterMargin replyContainer")

// form 요소 추가
let replyForm = document.createElement('form');
replyForm.setAttribute("action", "/comments/replyCreate")
replyForm.setAttribute("method", "post");
replyForm.setAttribute("id", "replyForm");

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
replySubmit.setAttribute("class", "btn btn-outline-danger");
replySubmit.setAttribute("type", "submit");
replySubmit.setAttribute("form", "replyForm");
replySubmit.textContent = "쓰기";

// commentId 요소
let commentIdElem = document.createElement('input');
commentIdElem.setAttribute("type", "hidden");
commentIdElem.setAttribute("name", "articleCommentId");
commentIdElem.setAttribute("value", -1);


// csrf 토큰, articleId 복제
let csrfToken = document.getElementsByName('_csrf')[0];
let cloneCSRF = csrfToken.cloneNode();
let articleId = document.getElementsByName('articleId')[0];
let cloneArticleId = articleId.cloneNode();
replyForm.appendChild(cloneCSRF);
replyForm.appendChild(cloneArticleId);
replyForm.appendChild(commentIdElem);

// 답글 요소들 조립
replyButtonsDiv.appendChild(replyCancel);
replyButtonsDiv.appendChild(replySubmit);
replyForm.appendChild(replyTextarea);
replyDiv.appendChild(replyForm);
replyDiv.appendChild(replyButtonsDiv);

// 열거형 선언
const transformableType = {
    Add_To_FirstChild: 0,
    Add_To_ThisNext: 1,
    Edit_Comment: 2,
    Edit_Reply: 3
};
// 열거형 불변화
Object.freeze(transformableType);


// 취소 시 답글 요소 삭제
replyCancel.addEventListener('click', (myself) => {
    replyDiv.nextElementSibling.removeAttribute("hidden");
    replyTextarea.value = "";
    myself.target.parentNode.parentNode.parentNode.removeChild(replyDiv);
})

let replyButtons = document.getElementsByClassName('transformable');



// 답글쓰기 요소에 이벤트 추가
for (let i = 0; i < replyButtons.length; i++) {
//    console.log(replyButtons[i]);
    replyButtons[i].addEventListener('click', (myself) => {
//        myself.target.parentNode.parentNode.insertBefore(replyDiv, myself.target.parentNode.nextElementSibling);
//        console.log(myself.target.parentNode.getAttribute('class').includes("ReplyItem"));

//        if (myself.target.parentNode.getAttribute('class').includes("ReplyItem")) {
//            myself.target.parentNode.parentNode.insertBefore(replyDiv, myself.target.parentNode.nextElementSibling);
//        } else {
//            myself.target.parentNode.lastElementChild.insertBefore(replyDiv, myself.target.parentNode.lastElementChild.firstElementChild);
//        }
//
//        // 답글을 작성할 댓글 id 값 가져오기
//        commentIdElem.value = replyDiv.parentNode.parentNode.getAttribute("id");

//        이거는 그냥 문자열이다.
//        console.log(myself.target.getAttribute('value'));

        switch(Number(myself.target.getAttribute('value'))) {
            case transformableType.Add_To_FirstChild:
                console.log("AddToFirst");
                myself.target.parentNode.lastElementChild.insertBefore(replyDiv, myself.target.parentNode.lastElementChild.firstElementChild);
                commentIdElem.value = replyDiv.parentNode.parentNode.getAttribute("id");
                break;
            case transformableType.Add_To_ThisNext:
                console.log("AddToThisNext");
                myself.target.parentNode.parentNode.insertBefore(replyDiv, myself.target.parentNode.nextElementSibling);
                commentIdElem.value = replyDiv.parentNode.parentNode.getAttribute("id");
                break;
            case transformableType.Edit_Comment:
                console.log("Edit_Comment");
                myself.target.parentNode.parentNode.insertBefore(
                        replyDiv, myself.target.parentNode.parentNode.getElementsByClassName("CommentReplyContent")[0]);
                commentIdElem.value = replyDiv.parentNode.getAttribute("id");
                replyTextarea.value = replyDiv.parentNode.getElementsByClassName("CommentReplyContent")[0].textContent;
                replyDiv.nextElementSibling.setAttribute("hidden", "hidden");
                break;
            case transformableType.Edit_Reply:
                console.log("Edit_Reply");
                myself.target.parentNode.parentNode.insertBefore(
                        replyDiv, myself.target.parentNode.parentNode.getElementsByClassName("CommentReplyContent")[0]);
                commentIdElem.value = replyDiv.parentNode.parentNode.parentNode.getAttribute("id");
                replyTextarea.value = replyDiv.parentNode.getElementsByClassName("CommentReplyContent")[0].textContent;
                replyDiv.nextElementSibling.setAttribute("hidden", "hidden");
                break;
        };
    })
}