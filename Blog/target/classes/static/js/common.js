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

function Doajax(url, type, data) {
    var callback = null;
    $.ajax({
        async: false,
        type: type,
        url: url,
        data: data,
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

/**
 * 修改用户信息
 */
$("#modUser").click(function () {
    $("#userpandle").slideToggle();
    let supperLink1 = supperLink("/user/userInfo", "GET");
    var data = supperLink1.data;
    console.log(data);
    $("#avatar").attr("src", data.avatar);
    $("#nickName").val(data.nickName);
    $("#myemail").val(data.email);
    if (data.sex === '1') {
        $("#nan").attr("checked", "checked")
    } else {
        $("#nv").attr("checked", "checked")
    }
    $("#uploadBar").hide();
    $("#modMessageBar").attr("class", "col-md-10");
})
//点击头像后更新视图
$("#avatar").click(function () {
    $("#modMessageBar").attr("class", "col-md-5");
    $("#uploadBar").show();
})

/**
 * 检测两次输入的密码是否一致
 * @returns {boolean}
 */
function checkpassword() {
    let val = $("#inputPassword1").val();
    let val1 = $("#inputPassword2").val();
    if (val !== val1) {
        new $.zui.Messager('警告信息：前后两次密码不一致', {
            type: 'danger' // 定义颜色主题
        }).show();
        return false;
    }
    return true;
}

//当第二遍密码输入完成时检测是否一致
$("#inputPassword2").change(
    function () {
        checkpassword();
    })
//修改信息提交
$("#modityUser").click(function () {
    let nick= $("#nickName").val();
    let em = $("#myemail").val();
    let sex = $("input[name='radioOptions']:checked").val();;
    if (checkpassword()) {
       let pass = $("#inputPassword1").val();
       let doajax = Doajax("/user/updataUser","post",{
           nickName:nick,
           password:pass,
           email:em,
           sex: sex
        });
       console.log(doajax);
    }
})
/**
 * <!--帐号注销-->
 */
$("#logout").click(function () {
    $.ajax({
        async: false,
        type: "GET",
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
            + "<a href='/articleList/?pageNum=1&pageSize=10&categoryId=" + data[i].id + "'>" + data[i].name + "</a>"
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