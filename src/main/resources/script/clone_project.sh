#!/usr/bin/env bash
#set -e

basedir=$(pwd)
access_token=$GITLAB_ACCESS_TOKEN

clone_project(){
    # 先做目录修正
    mkdir -p "$4"
    [ -d "$3" ] && mv "$3" "$4"
    #再做克隆
    if [ -d "$4"/"$3" ]; then
        echo "pull $1"
        cd "$4"/"$3" || exit 255
        git checkout "$2"
        git pull
    else
        echo "clone $1"
        cd "$4" || exit 254
        git clone "$1" "$3"
        cd "$3" || exit 253
        git checkout "$2"
    fi
    cd "$basedir" || exit 252
}

#clone_project http://gitlab-ci-token:"$access_token"@some-gitlab.domain/some-group/some-project.git master some-project some-group
clone_project http://gitlab-ci-token:"$access_token"@"$1" "$2" "$3" "$4"
