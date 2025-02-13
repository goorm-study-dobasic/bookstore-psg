document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("submitBtn").disabled = true; // 기본적으로 회원가입 버튼 비활성화

    var emailInput = document.getElementById("email");
    var emailCheckBtn = document.querySelector("button[onclick='checkEmail()']");
    var resetBtn = document.getElementById("resetBtn");
    var passwordConfirmInput = document.getElementById("passwordConfirm");

    if (emailCheckBtn) {
        emailCheckBtn.addEventListener("click", checkEmail);
    }
    if (resetBtn) {
        resetBtn.addEventListener("click", resetCheckEmail);
    }
    if (passwordConfirmInput) {
        passwordConfirmInput.addEventListener("keyup", checkPasswordMatch);
    }
});

// 이메일 중복 확인
function checkEmail() {
    var email = document.getElementById("email").value;
    if (!email) {
        alert("이메일을 입력해주세요.");
        return;
    }
    var submitBtn = document.getElementById("submitBtn");
    var resetBtn = document.getElementById("resetBtn");

    fetch("/join/duplicate/email?email=" + encodeURIComponent(email))
        .then(response => response.json())
        .then(data => {
            const resultElement = document.getElementById("emailCheckResult");
            if (data.exists) {
                resultElement.textContent = "이미 사용 중인 이메일입니다.";
                resultElement.style.color = "red";
                submitBtn.disabled = true;
            } else {
                resultElement.textContent = "사용 가능한 이메일입니다.";
                resultElement.style.color = "green";
                submitBtn.disabled = false;
                resetBtn.hidden = false;
                document.getElementById('email').readOnly = true;
            }
        })
        .catch(error => {
            console.error("AJAX 요청 실패: ", error);
        });
}

// 이메일 입력 재설정
function resetCheckEmail() {
    document.getElementById("email").value = "";
    document.getElementById("emailCheckResult").textContent = "";
    document.getElementById("submitBtn").disabled = true;
    document.getElementById("resetBtn").hidden = true;
    document.getElementById("email").readOnly = false;
}

// 비밀번호 일치 확인
function checkPasswordMatch() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("passwordConfirm").value;
    var message = document.getElementById("passwordMatchMessage");
    var submitBtn = document.getElementById("submitBtn");

    if (password === confirmPassword && password.length > 0) {
        message.textContent = "비밀번호가 일치합니다.";
        message.style.color = "green";
        submitBtn.disabled = false;
    } else {
        message.textContent = "비밀번호가 일치하지 않습니다.";
        message.style.color = "red";
        submitBtn.disabled = true;
    }
}

// 비밀번호 유효성 검사 (onsubmit 이벤트)
function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("passwordConfirm").value;

    if (password !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }
    return true;
}
