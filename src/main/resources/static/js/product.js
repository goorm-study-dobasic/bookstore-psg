document.addEventListener("DOMContentLoaded", function () {
    const buyNowButton = document.getElementById("buyNowButton");
    const quantityInput = document.getElementById("quantityInput");
    const buyNowForm = document.getElementById("buyNowForm");
    const buyNowQuantity = document.getElementById("buyNowQuantity");
    const errorMessage = document.getElementById("buyNowErrorMessage");
    const stockInfo = document.getElementById("stockInfo");

    // 상품의 남은 재고 가져오기
    const availableStock = parseInt(stockInfo.dataset.stock);

    buyNowButton.addEventListener("click", function () {
        const quantity = parseInt(quantityInput.value);

        if (quantity > availableStock) {
            errorMessage.textContent = "남은 재고보다 많이 구매할 수 없습니다.";
            errorMessage.style.display = "block";
        } else {
            errorMessage.style.display = "none";
            buyNowQuantity.value = quantity;
            buyNowForm.submit();
        }
    });
});
