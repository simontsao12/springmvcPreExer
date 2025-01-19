function delCust(cid) {
    if (confirm('是否確認刪除?')) {
        window.location.href = "customer.do?cid=" + cid +'&operate=del';
    }
}
function page(pageNo) {
    window.location.href = "customer.do?pageNo=" + pageNo;
}