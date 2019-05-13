





default rel

global _lib_newline
global _lib_printInt
global _lib_printlnInt
global _lib_print
global _lib_println
global _lib_getString
global _lib_getInt
global _lib_toString
global _lib_str_length
global _lib_str_ord
global _lib_str_substring
global _lib_str_parseInt
global _lib_strcat
global _lib_strcmp
global _lib_alloc
global main

extern strcmp
extern memcpy
extern strtol
extern strncpy
extern strlen
extern __sprintf_chk
extern __stack_chk_fail
extern __isoc99_scanf
extern malloc
extern puts
extern fputs
extern __printf_chk
extern _IO_putc
extern _GLOBAL_OFFSET_TABLE_
extern stdout


SECTION .text   6

_lib_newline:
        mov     rsi, qword [rel stdout]
        mov     edi, 10
        jmp     _IO_putc







ALIGN   16

_lib_printInt:
        lea     rsi, [rel .LC0]
        mov     rdx, rdi
        xor     eax, eax
        mov     edi, 1
        jmp     __printf_chk







ALIGN   16

_lib_printlnInt:
        lea     rsi, [rel .LC1]
        mov     rdx, rdi
        xor     eax, eax
        mov     edi, 1
        jmp     __printf_chk







ALIGN   16

_lib_print:
        mov     rsi, qword [rel stdout]
        jmp     fputs






ALIGN   8

_lib_println:
        jmp     puts


        nop





ALIGN   16

_lib_getString:
        push    rbx
        mov     edi, 256
        call    malloc
        lea     rdi, [rel .LC2]
        mov     rbx, rax
        mov     rsi, rax
        xor     eax, eax
        call    __isoc99_scanf
        mov     rax, rbx
        pop     rbx
        ret







ALIGN   16

_lib_getInt:
        sub     rsp, 24
        lea     rdi, [rel .LC0]


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+8H], rax
        xor     eax, eax
        lea     rsi, [rsp+4H]
        mov     dword [rsp+4H], 0
        call    __isoc99_scanf
        mov     rdx, qword [rsp+8H]


        xor     rdx, qword [fs:abs 28H]
        mov     eax, dword [rsp+4H]
        jnz     L_001
        add     rsp, 24
        ret

L_001:  call    __stack_chk_fail




ALIGN   8

_lib_toString:
        push    rbp
        push    rbx
        mov     rbp, rdi
        mov     edi, 256
        sub     rsp, 8
        call    malloc
        lea     rcx, [rel .LC0]
        mov     rbx, rax
        mov     r8, rbp
        mov     rdi, rax
        mov     edx, 256
        mov     esi, 1
        xor     eax, eax
        call    __sprintf_chk
        add     rsp, 8
        mov     rax, rbx
        pop     rbx
        pop     rbp
        ret






ALIGN   8

_lib_str_length:
        sub     rsp, 8
        call    strlen
        add     rsp, 8
        ret






ALIGN   8

_lib_str_ord:
        movsxd  rsi, esi
        movsx   eax, byte [rdi+rsi]
        ret






ALIGN   16

_lib_str_substring:
        push    r12
        push    rbp
        movsxd  rbp, esi
        push    rbx
        mov     ebx, edx
        mov     r12, rdi
        sub     ebx, ebp
        lea     edi, [rbx+2H]
        movsxd  rdi, edi
        call    malloc
        lea     edx, [rbx+1H]
        lea     rsi, [r12+rbp]
        mov     rdi, rax
        movsxd  rdx, edx
        call    strncpy
        pop     rbx
        pop     rbp
        pop     r12
        ret


_lib_str_parseInt:
        mov     edx, 10
        xor     esi, esi
        jmp     strtol






ALIGN   8

_lib_strcat:
        push    r14
        push    r13
        mov     r13, rsi
        push    r12
        push    rbp
        mov     r14, rdi
        push    rbx
        call    strlen
        mov     rdi, r13
        mov     rbx, rax
        call    strlen
        lea     rdi, [rbx+rax+1H]
        mov     rbp, rax
        call    malloc
        mov     rdx, rbx
        mov     r12, rax
        mov     rsi, r14
        mov     rdi, rax
        call    memcpy
        lea     rdi, [r12+rbx]
        lea     rdx, [rbp+1H]
        mov     rsi, r13
        call    memcpy
        pop     rbx
        mov     rax, r12
        pop     rbp
        pop     r12
        pop     r13
        pop     r14
        ret






ALIGN   16

_lib_strcmp:
        sub     rsp, 8
        call    strcmp
        add     rsp, 8
        cdqe
        ret


_lib_alloc:
        push    r15
        push    r14
        mov     r14, rdi
        push    r13
        push    r12
        mov     r15, rsi
        push    rbp
        push    rbx
        sub     rsp, 216
        mov     rbx, qword [rsi]
        mov     qword [rsp], rdi
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     r14, 1
        mov     qword [rsp+0C8H], rax
        mov     qword [rax], rbx
        je      L_019
        test    rbx, rbx
        jle     L_019
        lea     rdx, [rax+8H]
        add     rbp, rax
        lea     rax, [r15-48H]
        mov     qword [rsp+0C0H], rbp
        mov     r14, r15
        mov     qword [rsp+0B0H], rdx
        mov     qword [rsp+40H], rax
L_002:  mov     rbx, qword [r14-8H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     qword [rsp], 2
        mov     qword [rsp+0B8H], rax
        mov     qword [rax], rbx
        je      L_018
        test    rbx, rbx
        jle     L_018
        lea     rsi, [rax+8H]
        add     rbp, rax
        mov     qword [rsp+0A8H], rbp
        mov     qword [rsp+98H], rsi
L_003:  mov     rbx, qword [r14-10H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     qword [rsp], 3
        mov     qword [rsp+0A0H], rax
        mov     qword [rax], rbx
        je      L_017
        test    rbx, rbx
        jle     L_017
        lea     rsi, [rax+8H]
        add     rbp, rax
        mov     qword [rsp+90H], rbp
        mov     qword [rsp+78H], rsi
L_004:  mov     rbx, qword [r14-18H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     qword [rsp], 4
        mov     qword [rsp+88H], rax
        mov     qword [rax], rbx
        je      L_016
        test    rbx, rbx
        jle     L_016
        lea     rcx, [rax+8H]
        add     rbp, rax
        mov     qword [rsp+80H], rbp
        mov     qword [rsp+60H], rcx
L_005:  mov     rbx, qword [r14-20H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     qword [rsp], 5
        mov     qword [rsp+70H], rax
        mov     qword [rax], rbx
        je      L_015
        test    rbx, rbx
        jle     L_015
        lea     rdx, [rax+8H]
        add     rbp, rax
        mov     r15, r14
        mov     qword [rsp+68H], rbp
        mov     qword [rsp+50H], rdx
L_006:  mov     rbx, qword [r15-28H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        mov     rcx, qword [rsp]
        mov     qword [rsp+58H], rax
        mov     qword [rax], rbx
        cmp     rcx, 6
        je      L_014
        test    rbx, rbx
        jle     L_014
        lea     rdx, [rax+8H]
        add     rbp, rax
        lea     rax, [rcx-9H]
        mov     qword [rsp+48H], rbp
        mov     qword [rsp+28H], rdx
        mov     qword [rsp+8H], rax
L_007:  mov     rbx, qword [r15-30H]
        lea     rbp, [rbx*8+8H]
        mov     rdi, rbp
        call    malloc
        cmp     qword [rsp], 7
        mov     qword [rsp+30H], rax
        mov     qword [rax], rbx
        je      L_013
        test    rbx, rbx
        jle     L_013
        lea     rdx, [rax+8H]
        add     rbp, rax
        mov     qword [rsp+20H], rbp
        mov     qword [rsp+18H], rdx
L_008:  mov     rbx, qword [r15-38H]
        lea     r12, [rbx*8+8H]
        mov     rdi, r12
        call    malloc
        cmp     qword [rsp], 8
        mov     r14, rax
        mov     qword [rax], rbx
        jz      L_012
        test    rbx, rbx
        jle     L_012
        lea     rbp, [rax+8H]
        lea     rax, [rax+r12]
        mov     qword [rsp+38H], r14
        mov     qword [rsp+10H], rax
L_009:  mov     rbx, qword [r15-40H]
        lea     r12, [rbx*8+8H]
        mov     rdi, r12
        call    malloc
        cmp     qword [rsp], 9
        mov     r14, rax
        mov     qword [rax], rbx
        jz      L_011
        test    rbx, rbx
        jle     L_011
        lea     r13, [rax+8H]
        lea     rbx, [rax+r12]
        mov     r12, r13
        mov     r13, qword [rsp+40H]
L_010:  mov     rdi, qword [rsp+8H]
        mov     rsi, r13
        add     r12, 8
        call    _lib_alloc
        mov     qword [r12-8H], rax
        cmp     r12, rbx
        jnz     L_010
L_011:  mov     qword [rbp], r14
        add     rbp, 8
        cmp     qword [rsp+10H], rbp
        jnz     L_009
        mov     r14, qword [rsp+38H]
L_012:  mov     rax, qword [rsp+18H]
        mov     qword [rax], r14
        add     rax, 8
        cmp     qword [rsp+20H], rax
        mov     qword [rsp+18H], rax
        jne     L_008
L_013:  mov     rax, qword [rsp+28H]
        mov     rcx, qword [rsp+30H]
        add     rax, 8
        mov     qword [rax-8H], rcx
        cmp     qword [rsp+48H], rax
        mov     qword [rsp+28H], rax
        jne     L_007
L_014:  mov     rax, qword [rsp+50H]
        mov     rdx, qword [rsp+58H]
        add     rax, 8
        mov     qword [rax-8H], rdx
        cmp     qword [rsp+68H], rax
        mov     qword [rsp+50H], rax
        jne     L_006
        mov     r14, r15
L_015:  mov     rax, qword [rsp+60H]
        mov     rcx, qword [rsp+70H]
        add     rax, 8
        mov     qword [rax-8H], rcx
        cmp     qword [rsp+80H], rax
        mov     qword [rsp+60H], rax
        jne     L_005
L_016:  mov     rax, qword [rsp+78H]
        mov     rcx, qword [rsp+88H]
        add     rax, 8
        mov     qword [rax-8H], rcx
        cmp     qword [rsp+90H], rax
        mov     qword [rsp+78H], rax
        jne     L_004
L_017:  mov     rax, qword [rsp+98H]
        mov     rsi, qword [rsp+0A0H]
        add     rax, 8
        mov     qword [rax-8H], rsi
        cmp     qword [rsp+0A8H], rax
        mov     qword [rsp+98H], rax
        jne     L_003
L_018:  mov     rax, qword [rsp+0B0H]
        mov     rdx, qword [rsp+0B8H]
        add     rax, 8
        mov     qword [rax-8H], rdx
        cmp     qword [rsp+0C0H], rax
        mov     qword [rsp+0B0H], rax
        jne     L_002
L_019:  mov     rax, qword [rsp+0C8H]
        add     rsp, 216
        pop     rbx
        pop     rbp
        pop     r12
        pop     r13
        pop     r14
        pop     r15
        ret



SECTION .data


SECTION .bss


SECTION .rodata.str1.1

.LC0:
        db 25H, 64H, 00H

.LC1:
        db 25H, 64H, 0AH, 00H

.LC2:
        db 25H, 73H, 00H


SECTION .text.startup 6

main:
        xor     eax, eax
        ret


