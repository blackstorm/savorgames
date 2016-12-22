import os, re

from datetime import datetime
from fabric.api import *

env.user = 'root'
env.sudo_user = 'root'
env.hosts = ['127.0.0.1']
env.port = 10100
env.password = '(J6w[nrob(]Uu]Dg'

LOCAL_TAR_FILE = 'dist-savorgames.tar.gz'

REMOTE_DIR = '/srv/savorgames'

REMOTE_TEM_DIR = '/tmp'

def _current_path():
    return os.path.abspath('.')


def build():
    '''
    Build dist package.
    '''
    includes = ['*.crt','*.key']
    excludes = ['test', '.*', '*.pyc', '*.pyo']
    local('rm -f dist/%s' % LOCAL_TAR_FILE)
    with lcd(os.path.join(_current_path(), 'target')):
        cmd = ['tar', '--dereference', '-czvf', '../dist/%s' % LOCAL_TAR_FILE]
        cmd.extend(['--exclude=\'%s\'' % ex for ex in excludes])
        cmd.extend(includes)
        local(' '.join(cmd))


def deploy():
  put('dist/%s' % LOCAL_TAR_FILE, REMOTE_TEM_DIR)
