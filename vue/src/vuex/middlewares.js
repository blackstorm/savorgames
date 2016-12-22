import createLogger from 'vuex/logger';
import { dev } from '../config';

export default dev ? [createLogger()] : []; // 生产环境下设置为false
