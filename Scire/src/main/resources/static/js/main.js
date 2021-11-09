$(document).ready(function(){
    
    $('.menu-scire li:has(ul)').click(function(e){
        e.preventDefault();

        if ($(this).hasClass('activado')){
            $(this).removeClass('activado');
            $(this).children('ul').slideUp();
        } else {
            $('.menu-scire li ul').slideUp();
            $('.menu-scire li').removeClass('activado');
            $(this).addClass('activado');
            $(this).children('ul').slideDown();
        }
    });

    $('.btn-menu').click(function(){
        $('.contenedor-menu .menu-scire').slideToggle();
    });

    $(window).resize(function(){
        if ($(document).width() > 300){
            $('.contenedor-menu .menu-scire').css({'display': 'block'});
        }
        if ($(document).width() < 300){
            $('.contenedor-menu .menu-scire').css({'display': 'none'});
            $('.menu-scire li ul').slideUp();
            $('.menu li').removeClass('activado');
        }
    });
    
     $('.menu-scire li ul li a').click(function(){
        
        window.location.href= $(this).attr("href");
    });

   
/*
    // AGREGANDO CLASE ACTIVE AL PRIMER ENLACE ====================
	$('.menu-scire .category_item[category="all"]').addClass('ct_item-active');

    // FILTRANDO CATEGORIAS  ============================================

	$('.category_item').click(function(){
		var catCurso = $(this).attr('category');
		console.log(catCurso);

        // AGREGANDO CLASE ACTIVE AL ENLACE SELECCIONADO
		$('.category_item').removeClass('ct_item-active');
		$(this).addClass('ct_item-active');

		// OCULTANDO CURSOS =========================
		$('.curso-item').css('transform', 'scale(0)');
		function hideCurso(){
			$('.curso-item').hide();
		} setTimeout(hideCurso,400);

		// MOSTRANDO CURSOS =========================
		function showCurso(){
			$('.curso-item[category="'+catCurso+'"]').show();
			$('.curso-item[category="'+catCurso+'"]').css('transform', 'scale(1)');
		} setTimeout(showCurso,400);
	});

    // MOSTRANDO TODOS LOS CURSOS =======================

	$('.category_item[category="all"]').click(function(){
		function showAll(){
			$('.curso-item').show();
			$('.curso-item').css('transform', 'scale(1)');
		} setTimeout(showAll,400);
	});*/


   

});