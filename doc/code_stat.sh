#!/usr/bin/env sh
git log --since="$1" --pretty=tformat: --numstat
