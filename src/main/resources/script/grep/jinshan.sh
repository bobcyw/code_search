#!/usr/bin/env bash
#工作在代码扫描的目录下面
#全局扫描以后可以得到存在的文件后缀为，php，js，tsx，yml，java，所以可以对扫描优化一下
#grep -r --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' . > ksyun.com.txt
#grep -r --include="*.{php,js,tsx,yml,java}" --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' . > ksyun.com.txt
grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' "$1"
