;

jQuery( function() {
    $("body").on('click','[data-stopPropagation]',function (e) {
        e.stopPropagation();
    });
    
    // 滚动条
    const ps = new PerfectScrollbar('.layout-sidebar-scroll', {
		swipeEasing: false,
		suppressScrollX: true
	});
    
    // 侧边栏
    $(".aside-toggler").bind('click', function(){
        $('.layout-sidebar').toggleClass('aside-open');
        $("body").toggleClass('layout-sidebar-close');
        
        if ($('.mask-modal').length == 0) {
            $('<div class="mask-modal"></div>').prependTo('body');
        } else {
            $( '.mask-modal' ).remove();
        }
        $('.mask-modal').on( 'click', function() {
            $( this ).remove();
        	$('.layout-sidebar').toggleClass('aside-open');
            $('body').toggleClass('layout-sidebar-close');
        });
    });
    
	// 侧边栏导航
	$( '.nav-item-has-subnav > a' ).on( 'click', function() {
		$subnavToggle = jQuery( this );
		$navHasSubnav = $subnavToggle.parent();
        $topHasSubNav = $subnavToggle.parents('.nav-item-has-subnav').last();
		$subnav       = $navHasSubnav.find('.nav-subnav').first();
        $viSubHeight  = $navHasSubnav.siblings().find('.nav-subnav:visible').outerHeight();
        $scrollBox    = $('.layout-sidebar-scroll');
		$navHasSubnav.siblings().find('.nav-subnav:visible').slideUp(500).parent().removeClass('open');
		$subnav.slideToggle( 300, function() {
			$navHasSubnav.toggleClass( 'open' );
			
			// 新增滚动条处理
			var scrollHeight  = 0;
			    pervTotal     = $topHasSubNav.prevAll().length,
			    boxHeight     = $scrollBox.outerHeight(),
		        innerHeight   = $('.sidebar-main').outerHeight(),
                thisScroll    = $scrollBox.scrollTop(),
                thisSubHeight = $(this).outerHeight(),
                footHeight    = 121;
			
			if (footHeight + innerHeight - boxHeight >= (pervTotal * 48)) {
			    scrollHeight = pervTotal * 48;
			}
            if ($subnavToggle.parents('.nav-item-has-subnav').length == 1) {
                $scrollBox.animate({scrollTop: scrollHeight}, 300);
            } else {
                // 子菜单操作
                if (typeof($viSubHeight) != 'undefined' && $viSubHeight != null) {
                    scrollHeight = thisScroll + thisSubHeight - $viSubHeight;
                    $scrollBox.animate({scrollTop: scrollHeight}, 300);
                } else {
                    if ((thisScroll + boxHeight - $scrollBox[0].scrollHeight) == 0) {
                        scrollHeight = thisScroll - thisSubHeight;
                        $scrollBox.animate({scrollTop: scrollHeight}, 300);
                    }
                }
            }
		});
	});
    
    // 提示
	if($('[data-toggle="tooltip"]')[0]) {
		$('[data-toggle="tooltip"]').tooltip({
			"container" : 'body',
		});
	}
    
    // 弹出框
    if($('[data-toggle="popover"]')[0]) {
        $('[data-toggle="popover"]').popover();
    }
    
    // 标签
	if($('.js-tags-input')[0]) {
		$('.js-tags-input').tagsInput({
			height: '36px',
			width: '100%',
			defaultText: $('.js-tags-input').attr("placeholder"),
			removeWithBackspace: true,
			delimiter: [',']
		});
	}
    
    // 时间选择
	jQuery('.js-datetimepicker').each(function() {
		var $input = jQuery(this);
		$input.datetimepicker({
			format: $input.data('format') ? $input.data('format') : false,
			useCurrent: $input.data('use-current') ? $input.data('use-current') : false,
			locale: moment.locale('' + ($input.data('locale') ? $input.data('locale') : '') + ''),
			showTodayButton: $input.data('show-today-button') ? $input.data('show-today-button') : false,
			showClear: $input.data('show-clear') ? $input.data('show-clear') : false,
			showClose: $input.data('show-close') ? $input.data('show-close') : false,
			sideBySide: $input.data('side-by-side') ? $input.data('side-by-side') : false,
			inline: $input.data('inline') ? $input.data('inline') : false,
		});
	});
    
    // 日期选择
	jQuery('.js-datepicker').each(function() {
	    var $input = jQuery(this);
		$input.datepicker({
			weekStart: 1,
			autoclose: true,
			todayHighlight: true,
			language: 'zh-CN',
		});
	});
    
    // 颜色选取
	jQuery('.js-colorpicker').each(function() {
		var $colorpicker = jQuery(this);
		var $colorpickerMode = $colorpicker.data('colorpicker-mode') ? $colorpicker.data('colorpicker-mode') : 'hex';
		var $colorpickerinline = $colorpicker.data('colorpicker-inline') ? true: false;
		$colorpicker.colorpicker({
			'format': $colorpickerMode,
			'inline': $colorpickerinline
		});
	});
  
    // 复选框全选
	$("#check-all").change(function () {
		$("input[type='checkbox']").prop('checked', $(this).prop("checked"));
	});
    
    // 设置主题配色
	setTheme = function(input_name, data_name) {
	    $("input[name='"+input_name+"']").click(function(){
	        $('body').attr(data_name, $(this).val());
	    });
	}
	setTheme('site_theme', 'data-theme');
	setTheme('logo_bg', 'data-logobg');
	setTheme('header_bg', 'data-headerbg');
	setTheme('sidebar_bg', 'data-sidebarbg');

});