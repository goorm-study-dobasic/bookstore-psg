document.addEventListener("DOMContentLoaded", function () {
    let totalPrice = 0;

    document.querySelectorAll("tbody tr").forEach(row => {
        const quantity = parseInt(row.children[2].textContent);
        const price = parseInt(row.children[3].textContent);
        totalPrice += quantity * price;
    });

    document.getElementById("totalPrice").textContent = totalPrice.toLocaleString();
});
