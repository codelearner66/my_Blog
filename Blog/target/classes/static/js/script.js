$(function () {
    $('.forgotBtn').click(function () {
        $('#forgot').toggleClass('toggle')
    })

    $('.registerBtn').click(function () {
        $('#register, #formContainer').toggleClass('toggle')
    })
})