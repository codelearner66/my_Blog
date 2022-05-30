/**
 * 立即执行函数 页面文件加载完成后执行
 * 如果本地有token 将token 复制到cookie并直接跳转到主页
 * 建议使用在 登录页面
 */
(function(){
    let item = localStorage.getItem("token");
    if (item !== undefined&&item!==null) {
        document.cookie = 'token_cookie='+item;
        $.ajax({
            async : false,
            type: "post",
            url: "/login",
            headers: {'token': item},
            success : function(t) {
                if(t.code===500){
                    location.href="/";//有用！
                }
               else{
                    localStorage.removeItem("token");
                    window.location.reload();
                }
            }
        });
    }
})();
$(function () {
    $('.forgotBtn').click(function () {
        $('#forgot').toggleClass('toggle')
    })

    $('.registerBtn').click(function () {
        $('#register, #formContainer').toggleClass('toggle')
    })
})