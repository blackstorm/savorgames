export default {
  // 添加 class
  addClass(dom, className) {
    dom.classList.add(className);
  },
  removeClass(dom, className) {
    dom.classList.remove(className);
  },
  // 获取宽度
  width(dom) {
    return dom.offsetWidth;
  },
  height(dom) {
    return dom.offsetHeight;
  },
  transform(dom, style) {
    dom.style.transform = style;
  }
};
