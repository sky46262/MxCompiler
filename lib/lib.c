#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <ctype.h>

void _lib_newline() {
    putchar('\n');
}

void _lib_printInt(long long num) {
    printf("%d",num);
}

void _lib_printlnInt(long long num) {
    printf("%d\n",num);
}

void _lib_print(const char* str) {
    fputs(str,stdout);
}

void _lib_println(const char *str){
    puts(str);
}

char* _lib_getString() {
    char *t = (char*)malloc(256);
    scanf("%s",t);
    /*char c;*/
    /*int i=0;*/
    /*while (isspace(c = getchar()));*/
    /*while (c!=EOF) {*/
        /*if (isspace(c)) break;*/
        /*t[i++] = c;*/
        /*c = getchar();*/
    /*}*/
    /*t[i] = '\0';*/
    return t;
    /*return fgets(t,100,stdin);*/
}

int _lib_getInt() {
    int ret = 0;
    scanf("%d",&ret);
    /*char c;*/
    /*int sgn = 0;*/
    /*int ret = 0;*/
    /*while(isspace(c = getchar()));*/
    /*if (c == '-') sgn = 1;*/
    /*else if (!isdigit(c) && c != EOF && c != '+') {*/
        /*ungetc(c,stdin);*/
        /*return 0;*/
    /*}*/
    /*for (;isdigit(c);c=getchar()){*/
        /*ret = ret * 10 + (c - '0');*/
    /*}*/
    /*if (sgn) ret = -ret;*/
    return ret;
}

char* _lib_toString(long long num) {
    char *str = (char*)malloc(256);
    sprintf(str,"%d",num);
    /*int i = 0,k = 0;*/
    /*if (num<0) {*/
        /*num = -num;*/
        /*str[i++] = '-';*/
        /*k = 1;*/
    /*}*/
    /*do {*/
        /*str[i++] = '0' + num % 10;*/
        /*num /= 10;*/
    /*} while(num);*/
    /*str[i] = '\0';*/
    /*char tmp;*/
    /*for (;k<=(i-1)>>1;++k){*/
        /*int j = i - k - 1;*/
        /*tmp = str[k];*/
        /*str[k] = str[j];*/
        /*str[j] = tmp;*/
    /*}*/
    return str;
}

int _lib_str_length(const char *t){
    return strlen(t);
}

int _lib_str_ord(const char *t, int pos) {
    return (int) t[pos];
}

char* _lib_str_substring(const char* t, int l, int r){
    int len = r - l + 1;
    char *ret = (char*)malloc(len+1);
    strncpy(ret,t+l,len);
    return ret;
}

long long _lib_str_parseInt(const char *t){
    return atol(t);
    /*int num = 0, sgn = 0;*/
    /*while(isspace(*(t))) t++;*/
    /*if (*t == '-') sgn = 1;*/
    /*else if (!isdigit(*t) && *t != '+') return 0;*/
    /*for (;isdigit(*t);++t) {*/
        /*num = num * 10 + *t - '0';*/
    /*}*/
    /*if (sgn) num = -num;*/
    /*return num;*/
}

char* _lib_strcat(const char *s1, const char *s2) {
    /*char *ret = malloc(256);*/
    char *ret = (char*)malloc(strlen(s1)+strlen(s2)+1);
    strcpy(ret,s1);
    strcat(ret,s2);
    return ret;
}

int _lib_strcmp(const char *s1, const char *s2) {
    return strcmp(s1, s2);
}

long long* _lib_alloc(long long rem, long long *dims) {
    long long dim = *dims;
    long long* arr = (long long*)malloc((1+dim)*8);
    *arr = dim;
    if (rem==1) return arr;
    for (int i = 1;i<=dim;++i) {
        *(arr + i) = (long long)_lib_alloc(rem-1,dims-1);
    }
    return arr;
}

int main(){
    /*long long dims[4] = {2,3,4,5};*/
    /*long long ***arr = (long long ***)_lib_alloc(sizeof(long long),3,dims);*/
    /*puts("gg");*/
    /*for (int i=0;i<dims[0];++i) {*/
        /*for (int j=0;j<dims[1];++j) {*/
            /*for (int k=0;k<dims[2];++k) {*/
                /*arr[i][j][k] = 1;*/
                /*[>*((long long*)(*((long long*)(*(arr+i))+j)+k)) = 1;<]*/
            /*}*/
            /*[>arr[i][j] = 1;<]*/
            /*[>*(long long*)*((long long*)((long long*)(*arr)+i))<]*/
        /*}*/
        /*[>arr[i] = 1;<]*/
    /*}*/
    return 0;
}
