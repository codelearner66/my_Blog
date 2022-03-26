/**
 * 登录函数接收请求地址 和登录所需的数据
 * 登陆成功就解析出token存入本地
 * 错误就返回提示信息
 * @param url  请求地址
 * @param data  登录所需的数据
 * @returns {null}返回提示信息
 */
function ajaxpost(url, data) {
    var callback = null;
    $.ajax({
        async: false,
        type: 'post',
        url: url,
        data: data,
        success: function (t) {
            for (const tKey in t) {
                if (tKey === "data") {
                    console.log("tKey---->" + tKey);
                    for (const tKeyKey in t.data) {
                        console.log("tKeyKey---->" + tKeyKey);
                        if (tKeyKey === "token") {
                            localStorage.setItem("token", t.data.token);
                            document.cookie = 'token_cookie=' + t.data.token;
                            location.href = "/";
                            break;
                        }
                    }
                    break;
                }
            }
            callback = t;
        },
        error: function (e) {
            callback = e;
        }
    });
    return callback;
}

/**
 * 带有数据和返回值的请求
 * @param url 请求地址
 * type 请求方式
 * @returns {null}  返回的信息
 */
function supperLink(url, type) {
    var callback = null;
    $.ajax({
        async: false,
        type: type,
        url: url,
        success: function (t) {
            callback = t;
        },
        error: function (e) {
            callback = e;
        }
    });
    return callback;
}

/**
 * 页面跳转
 * @param url 跳转地址
 */
function pageTurns(url) {
    location.href = url;
}

<!--帐号注销-->
$("#logout").click(function () {
    $.ajax({
        async: false,
        type: "POST",
        url: "/logout",
        success: function (t) {
            localStorage.removeItem("token");
            window.location.reload();
        },
        error: function (e) {
            callback = e;
        }
    });
    window.location.reload();
})
//分类下拉列表
let $select = $("#select");
let $h = $("#h-hid");
$select.mouseenter(function () {
    $("#select-up").show();
    $("#select-down").hide();
    $h.slideDown();
    let supperLink1 = supperLink("/category/category", "get");
    var data = supperLink1.data;
    console.log(data)
    for (let i = 0; i < data.length; i++) {
        var html = "<div class='col-md-12 select-item'> "
            + "<a href='/articleView/" + data[i].id + "'>" + data[i].name + "</a>"
            + "</div>";
        $("#h-hid").append(html);
    }
})
$select.mouseleave(function () {
    $("#select-up").hide();
    $("#select-down").show();
    $h.slideUp();
   $h.empty();
})