#!/usr/bin/env bash
#set -e

basedir=$(pwd)

clone_project(){
    #再做克隆
    if [ -d "$3" ]; then
        echo "pull $1"
        cd "$3" || exit 255
        git checkout "$2"
        git pull
    else
        echo "clone $1"
        git clone "$1" "$3"
        cd "$3" || exit 253
        git checkout "$2"
    fi
    cd "$basedir" || exit 252
}

#clone_project http://gitlab-ci-token:"$access_token"@some-gitlab.domain/some-group/some-project.git master some-project
clone_project "${url}" "${branch}" "${destDirectory}"
