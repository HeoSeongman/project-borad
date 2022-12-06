// 댓글 쓰기 영역 구분
let ReplyWriterItem = document.createElement('div');
ReplyWriterItem.setAttribute("class", "ReplyWriter")

// text 요소 추가
let ReplyWriterTextarea = document.createElement('textarea');
ReplyWriterTextarea.setAttribute("class", "inputTextarea form-control");
ReplyWriterTextarea.setAttribute("name", "content");

// 답글 버튼 요소 구분
let ReplyWriterButtons = document.createElement('div');
ReplyWriterButtons.setAttribute("class", "postButtonsBottom");

// 답글 취소 버튼
let ReplyWriterCancel = document.createElement('a');
ReplyWriterCancel.setAttribute("class", "btn btn-primary");
ReplyWriterCancel.textContent = "취소";
// 취소 버튼 이벤트
ReplyWriterCancel.addEventListener('click', () => {
    if (ReplyWriterItem.parentElement != null) {
        ReplyWriterItem.parentElement.removeChild(ReplyWriterItem);
        ReplyWriterTextarea.value = "";
    }
});
// 답글쓰기 버튼 이벤트
[...document.getElementsByClassName("CommentWriteButton")].forEach( (button) => {
    writeButtonEvent(button);
});

function writeButtonEvent(button) {
    button.addEventListener("click", (myself) => {
        ReplyWriterCancel.click();
        myself.target.parentElement.parentElement.insertBefore(ReplyWriterItem, myself.target.parentElement.nextElementSibling);
    });
}

// 답글 제출 버튼
let ReplyWriterSubmit = document.createElement('button');
ReplyWriterSubmit.setAttribute("class", "btn btn-outline-primary");
ReplyWriterSubmit.setAttribute("type", "button");
ReplyWriterSubmit.textContent = "쓰기";
// 답글 제출
ReplyWriterSubmit.addEventListener("click", async () => {
    setRequestData("/createComment", {
                    articleId: document.getElementById("articleId").getAttribute("value"),
                    content: ReplyWriterTextarea.value,
                    parentCommentId: ReplyWriterItem.previousElementSibling.getAttribute("id"),
                    commentId: 0
        }
    );
    if (!checkContent(ReplyWriterTextarea)) return;

    let responseData = await postData();
    console.log(responseData);

    createAddComment(responseData);
    ReplyWriterCancel.click();
});

// 답글 요소들 조립
ReplyWriterButtons.appendChild(ReplyWriterCancel);
ReplyWriterButtons.appendChild(ReplyWriterSubmit);
ReplyWriterItem.appendChild(ReplyWriterTextarea);
ReplyWriterItem.appendChild(ReplyWriterButtons);


//----------------------------------------------------------------------------------------------------------------


// 댓글 수정 영역 구분
let ReplyModifierItem = document.createElement('div');
ReplyModifierItem.setAttribute("class", "ReplyModifier")

// text 요소 추가
let ReplyModifierTextarea = document.createElement('textarea');
ReplyModifierTextarea.setAttribute("class", "inputTextarea form-control");
ReplyModifierTextarea.setAttribute("name", "content");

// 답글 버튼 요소 구분
let ReplyModifierButtons = document.createElement('div');
ReplyModifierButtons.setAttribute("class", "postButtonsBottom");

// 답글 취소 버튼
let ReplyModifierCancel = document.createElement('a');
ReplyModifierCancel.setAttribute("class", "btn btn-primary");
ReplyModifierCancel.textContent = "취소";
// 취소 버튼 이벤트
ReplyModifierCancel.addEventListener('click', () => {
    if (ReplyModifierItem.parentElement != null) {
        ReplyModifierItem.nextElementSibling.removeAttribute("hidden");
        ReplyModifierItem.parentElement.removeChild(ReplyModifierItem);
        ReplyModifierTextarea.value = "";
    }
});
// 수정 버튼 이벤트
[...document.getElementsByClassName("CommentModifyButton")].forEach( (button) => {
    button.addEventListener("click", (myself) => {
        ReplyModifierCancel.click();

        myself.target.parentElement.parentElement.insertBefore(ReplyModifierItem, myself.target.parentElement.parentElement.children[1]);
        ReplyModifierTextarea.value = ReplyModifierItem.nextElementSibling.getElementsByClassName("CommentContent")[0].textContent;
        myself.target.parentElement.parentElement.children[2].setAttribute("hidden", "hidden");
    });
});

// 답글 제출 버튼
let ReplyModifierSubmit = document.createElement('button');
ReplyModifierSubmit.setAttribute("class", "btn btn-outline-primary");
ReplyModifierSubmit.setAttribute("type", "button");
ReplyModifierSubmit.textContent = "쓰기";
// 답글 제출
ReplyModifierSubmit.addEventListener("click", async () => {
    setRequestData("/updateComment", {
                        articleId: document.getElementById("articleId").getAttribute("value"),
                        content: ReplyModifierTextarea.value,
                        parentCommentId: ReplyModifierItem.parentElement.getAttribute("id"),
                        commentId: ReplyModifierItem.parentElement.getAttribute("id")
                    }
    );
    if (!checkContent(ReplyModifierTextarea)) return;

    let responseData = await postData();
    console.log(responseData);

    [...ReplyModifierItem.nextElementSibling.children]
        .filter( item => item.getAttribute("class") === "CommentContent" )[0]
            .textContent = responseData.content;
    ReplyModifierCancel.click();
});

// 답글 요소들 조립
ReplyModifierButtons.appendChild(ReplyModifierCancel);
ReplyModifierButtons.appendChild(ReplyModifierSubmit);
ReplyModifierItem.appendChild(ReplyModifierTextarea);
ReplyModifierItem.appendChild(ReplyModifierButtons);


//----------------------------------------------------------------------------------------------------------------

// 최상위 댓글 쓰기 버튼 이벤트
document.getElementById("createCommentButton").addEventListener("click", async (myself) => {
    setRequestData("/createComment", {
                    articleId: document.getElementById("articleId").getAttribute("value"),
                    content: myself.target.parentElement.previousElementSibling.value,
                    parentCommentId: 0,
                    commentId: 0
        }
    );
    if (!checkContent(myself.target.parentElement.previousElementSibling)) return;

    let responseData = await postData();
    console.log(responseData);

    createAddComment(responseData);
});



// 요청 데이터
let requestData = {
    requestURL: "",
    requestBody: {
    }
}

function setRequestData(URL, BodyData) {
    requestData.requestURL = URL,
    requestData.requestBody = BodyData
}

// 추가할 댓글,답글 생성 함수
function createAddComment(responseData) {
    // 추가될 댓글 요소 구분
    let add_div_Element = document.createElement("div");
    add_div_Element.setAttribute("class", "");
    add_div_Element.setAttribute("id", responseData.id);

    let add_createdBy = document.createElement("p");
    add_createdBy.setAttribute("class", "commentCreatedBy");
    add_createdBy.textContent = responseData.nickname;

    let add_contentItem = document.createElement("div");
    add_contentItem.setAttribute("class", "CommentContentItem");

    let add_content = document.createElement("span");
    add_content.setAttribute("class", "CommentContent");
    add_content.textContent = responseData.content;

    let add_createdAt = document.createElement("span");
    add_createdAt.setAttribute("class", "CreatedAt");
    add_createdAt.textContent = responseData.createdAt;

    let add_replyButton = document.createElement("a");
    add_replyButton.setAttribute("href", "javascript:void(0)");
    add_replyButton.setAttribute("class", "CommentWriteButton");
    add_replyButton.textContent = "답글쓰기"
    writeButtonEvent(add_replyButton);

    add_div_Element.appendChild(add_createdBy);
    add_div_Element.appendChild(add_contentItem);
    add_div_Element.appendChild(add_createdAt);
    add_div_Element.appendChild(add_replyButton);

    if (responseData.parentCommentId === 0) {
        add_div_Element.setAttribute("class", "CommentItem border-bottom");
        document.getElementsByClassName("blog-post-comments")[0].insertBefore(add_div_Element, null);
    } else {
        add_div_Element.setAttribute("class", "ReplyItem border-bottom");
        let add_ParentCommentNickname = document.createElement("span");
        add_ParentCommentNickname.setAttribute("class", "ParentCommentNickname");
        add_ParentCommentNickname.textContent =
            "@" + document.getElementById(responseData.parentCommentId).firstElementChild.textContent;
        add_contentItem.appendChild(add_ParentCommentNickname);
        let stopSearchTarget = document.getElementById(responseData.rootCommentId);
        while (stopSearchTarget.nextElementSibling != null) {
            if ([...stopSearchTarget.nextElementSibling.classList].find( item => item === "CommentItem") == undefined) {
                stopSearchTarget = stopSearchTarget.nextElementSibling;
                continue;
            }
            break;
        }
        stopSearchTarget.parentElement.insertBefore(add_div_Element, stopSearchTarget.nextElementSibling);
    }

    add_contentItem.appendChild(add_content);
}


async function checkContent(textarea) {
    if (textarea.value.trim().length === 0) {
        alert("올바르게 입력하세요.");
        return false;
    }
    return true;
}


// 삭제 버튼들 클릭 이벤트 등록
[...document.getElementsByClassName("deleteButton")].forEach( (button) => {
    button.addEventListener('click', async (myself) => {

        if (!confirm("삭제하시겠습니까?")) return;

        // Long 하나 받을때는 {} 로 객체형식이면 안된다.
        setRequestData("/deleteComment", myself.target.parentElement.parentElement.getAttribute("id"));

        let responseData = await postData();
        console.log(responseData);

    });
});

let _csrf = document.getElementById("_csrf").content;
let _csrf_header = document.getElementById("_csrf_header").content;

// 데이터 제출
async function postData() {
    let promiseData = await fetch("/comments" + requestData.requestURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': _csrf
        },
        body: JSON.stringify(requestData.requestBody),
    });
    return promiseData.json();
//    .then( (response) => console.log(response.json()) );
}