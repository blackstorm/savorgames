export createdAdminCssJs function() {
  var flatCss = document.createElement('link');
  flatCss.href = '//cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css';
  flatCss.rel = 'stylesheet';
  flatCss.id = 'flat-css';
  document.head.appendChild(flatCss);
}
