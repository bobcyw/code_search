#!/usr/bin/env bash
#grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' "$1"
grep -r <#if includes?has_content><#list includes as include>--include="${include}" </#list></#if><#if excludes?has_content><#list excludes as exclude>--exclude="${exclude}" </#list></#if> '${match}' '${directory}'
