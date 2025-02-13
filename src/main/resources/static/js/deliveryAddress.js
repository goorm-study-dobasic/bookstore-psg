document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("submitBtn").disabled = true; // 페이지 로드 시 기본적으로 버튼 비활성화

    document.getElementById("zipcode").addEventListener("click", findAddr);
    document.getElementById("streetAddr").addEventListener("click", findAddr);

    var checkAliasBtn = document.querySelector("button[onclick='checkAlias()']");
    if (checkAliasBtn) {
        checkAliasBtn.addEventListener("click", checkAlias);
    }
});

// 주소 찾기
function findAddr() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("detailAddr").value = extraAddr;
            } else {
                document.getElementById("detailAddr").value = '';
            }
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("streetAddr").value = addr;
            document.getElementById("detailAddr").focus();
        }
    }).open();
}

// 이메일 중복 확인
function checkAlias() {
    var addressName = document.getElementById("addressName").value;
    if (!addressName) {
        alert("별칭을 입력해주세요.");
        return;
    }
    var submitBtn = document.getElementById("submitBtn");

    console.log("별칭 중복 확인 요청: ", addressName); // 디버깅용 로그 추가

    fetch("/users/join/duplicate/alias?addressName=" + encodeURIComponent(addressName))
        .then(response => {
            console.log("서버 응답 상태 코드: ", response.status);
            return response.json();
        })
        .then(data => {
            console.log("서버 응답 데이터: ", data);
            const resultElement = document.getElementById("aliasCheckResult");
            if (data.exists) {
                resultElement.textContent = "이미 사용 중인 별칭입니다.";
                resultElement.style.color = "red";
                submitBtn.disabled = true;
            } else {
                resultElement.textContent = "사용 가능한 별칭입니다.";
                resultElement.style.color = "green";
                submitBtn.disabled = false;
            }
        })
        .catch(error => {
            console.error("AJAX 요청 실패: ", error);
        });
}