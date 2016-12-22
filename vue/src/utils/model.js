import $ from './dooom';

export default {
  show(dom) {
    var xcenter = Math.floor($.width(dom) / 2);
    var ycenter = Math.floor($.height(dom) / 1.1);
    // $('.pb2md').css('transform', 'translateX(-' + xcenter + 'px) translateY(-' + ycenter + 'px)');
    $.transform(dom, 'translateX(-' + xcenter + 'px) translateY(-' + ycenter + 'px)');
    var body = document.getElementsByTagName('body')[0];
    $.addClass(body, 'pb2md-show');
  },
  hidden(dom) {
    var body = document.getElementsByTagName('body')[0];
    $.removeClass(body, 'pb2md-show');
    $.removeClass(dom, 'pb2md-show');
  }
};
