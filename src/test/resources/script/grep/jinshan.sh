#!/usr/bin/env bash
#工作在代码扫描的目录下面
#grep -r --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' . > ksyun.com.txt
grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' "$1"
