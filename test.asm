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
extern strcat
extern strcpy
extern atol
extern strncpy
extern strlen
extern __stack_chk_fail
extern __isoc99_scanf
extern malloc
extern puts
extern fputs
extern stdout
extern printf
extern putchar
extern _GLOBAL_OFFSET_TABLE_


SECTION .text

_lib_newline:
        push    rbp
        mov     rbp, rsp
        mov     edi, 10
        call    putchar
        nop
        pop     rbp
        ret


_lib_printInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_014]
        mov     eax, 0
        call    printf
        nop
        leave
        ret


_lib_printlnInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_015]
        mov     eax, 0
        call    printf
        nop
        leave
        ret


_lib_print:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rdx, qword [rel stdout]
        mov     rax, qword [rbp-8H]
        mov     rsi, rdx
        mov     rdi, rax
        call    fputs
        nop
        leave
        ret


_lib_println:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    puts
        nop
        leave
        ret


_lib_getString:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     edi, 256
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_016]
        mov     eax, 0
        call    __isoc99_scanf
        mov     rax, qword [rbp-8H]
        leave
        ret


_lib_getInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16


        mov     rax, qword [fs:abs 28H]
        mov     qword [rbp-8H], rax
        xor     eax, eax
        mov     dword [rbp-0CH], 0
        lea     rax, [rbp-0CH]
        mov     rsi, rax
        lea     rdi, [rel L_014]
        mov     eax, 0
        call    __isoc99_scanf
        mov     eax, dword [rbp-0CH]
        mov     rdx, qword [rbp-8H]


        xor     rdx, qword [fs:abs 28H]
        jz      L_001
        call    __stack_chk_fail
L_001:  leave
        ret


_lib_toString:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 96
        mov     qword [rbp-58H], rdi


        mov     rax, qword [fs:abs 28H]
        mov     qword [rbp-8H], rax
        xor     eax, eax
        mov     dword [rbp-44H], 0
        mov     dword [rbp-40H], 0
        cmp     qword [rbp-58H], 0
        jns     L_002
        mov     dword [rbp-44H], 1
        neg     qword [rbp-58H]
L_002:  cmp     qword [rbp-58H], 0
        jnz     L_004
        add     dword [rbp-40H], 1
        mov     eax, dword [rbp-40H]
        cdqe
        mov     dword [rbp+rax*4-30H], 0
        jmp     L_005

L_003:  mov     rcx, qword [rbp-58H]
        mov     rdx, qword 6666666666666667H
        mov     rax, rcx
        imul    rdx
        sar     rdx, 2
        mov     rax, rcx
        sar     rax, 63
        sub     rdx, rax
        mov     rax, rdx
        shl     rax, 2
        add     rax, rdx
        add     rax, rax
        sub     rcx, rax
        mov     rdx, rcx
        add     dword [rbp-40H], 1
        mov     eax, dword [rbp-40H]
        cdqe
        mov     dword [rbp+rax*4-30H], edx
        mov     rcx, qword [rbp-58H]
        mov     rdx, qword 6666666666666667H
        mov     rax, rcx
        imul    rdx
        sar     rdx, 2
        mov     rax, rcx
        sar     rax, 63
        sub     rdx, rax
        mov     rax, rdx
        mov     qword [rbp-58H], rax
L_004:  cmp     qword [rbp-58H], 0
        jnz     L_003
L_005:  mov     edx, dword [rbp-40H]
        mov     eax, dword [rbp-44H]
        add     eax, edx
        add     eax, 1
        cdqe
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-38H], rax
        mov     edx, dword [rbp-40H]
        mov     eax, dword [rbp-44H]
        add     eax, edx
        movsxd  rdx, eax
        mov     rax, qword [rbp-38H]
        mov     qword [rax], rdx
        mov     edx, dword [rbp-40H]
        mov     eax, dword [rbp-44H]
        add     eax, edx
        movsxd  rdx, eax
        mov     rax, qword [rbp-38H]
        add     rax, rdx
        mov     byte [rax], 0
        cmp     dword [rbp-44H], 0
        jz      L_006
        mov     rax, qword [rbp-38H]
        mov     byte [rax], 45
L_006:  mov     dword [rbp-3CH], 0
        jmp     L_008

L_007:  mov     eax, dword [rbp-40H]
        sub     eax, dword [rbp-3CH]
        cdqe
        mov     eax, dword [rbp+rax*4-30H]
        lea     ecx, [rax+30H]
        mov     edx, dword [rbp-3CH]
        mov     eax, dword [rbp-44H]
        add     eax, edx
        movsxd  rdx, eax
        mov     rax, qword [rbp-38H]
        add     rax, rdx
        mov     edx, ecx
        mov     byte [rax], dl
        add     dword [rbp-3CH], 1
L_008:  mov     eax, dword [rbp-3CH]
        cmp     eax, dword [rbp-40H]
        jl      L_007
        mov     rax, qword [rbp-38H]
        mov     rsi, qword [rbp-8H]


        xor     rsi, qword [fs:abs 28H]
        jz      L_009
        call    __stack_chk_fail
L_009:  leave
        ret


_lib_str_length:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    strlen
        leave
        ret


_lib_str_ord:
        push    rbp
        mov     rbp, rsp
        mov     qword [rbp-8H], rdi
        mov     dword [rbp-0CH], esi
        mov     eax, dword [rbp-0CH]
        movsxd  rdx, eax
        mov     rax, qword [rbp-8H]
        add     rax, rdx
        movzx   eax, byte [rax]
        movsx   eax, al
        pop     rbp
        ret


_lib_str_substring:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 32
        mov     qword [rbp-18H], rdi
        mov     dword [rbp-1CH], esi
        mov     dword [rbp-20H], edx
        mov     eax, dword [rbp-20H]
        sub     eax, dword [rbp-1CH]
        add     eax, 1
        mov     dword [rbp-0CH], eax
        mov     eax, dword [rbp-0CH]
        add     eax, 1
        cdqe
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-8H], rax
        mov     eax, dword [rbp-0CH]
        movsxd  rdx, eax
        mov     eax, dword [rbp-1CH]
        movsxd  rcx, eax
        mov     rax, qword [rbp-18H]
        add     rcx, rax
        mov     rax, qword [rbp-8H]
        mov     rsi, rcx
        mov     rdi, rax
        call    strncpy
        mov     rax, qword [rbp-8H]
        leave
        ret


_lib_str_parseInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    atol
        leave
        ret


_lib_strcat:
        push    rbp
        mov     rbp, rsp
        push    rbx
        sub     rsp, 40
        mov     qword [rbp-28H], rdi
        mov     qword [rbp-30H], rsi
        mov     rax, qword [rbp-28H]
        mov     rdi, rax
        call    strlen
        mov     rbx, rax
        mov     rax, qword [rbp-30H]
        mov     rdi, rax
        call    strlen
        add     rax, rbx
        add     rax, 1
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-18H], rax
        mov     rdx, qword [rbp-28H]
        mov     rax, qword [rbp-18H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcpy
        mov     rdx, qword [rbp-30H]
        mov     rax, qword [rbp-18H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcat
        mov     rax, qword [rbp-18H]
        add     rsp, 40
        pop     rbx
        pop     rbp
        ret


_lib_strcmp:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     qword [rbp-10H], rsi
        mov     rdx, qword [rbp-10H]
        mov     rax, qword [rbp-8H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcmp
        cdqe
        leave
        ret


_lib_alloc:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 48
        mov     qword [rbp-28H], rdi
        mov     qword [rbp-30H], rsi
        mov     rax, qword [rbp-30H]
        mov     rax, qword [rax]
        mov     qword [rbp-10H], rax
        mov     rax, qword [rbp-10H]
        add     rax, 1
        shl     rax, 3
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rax, qword [rbp-8H]
        mov     rdx, qword [rbp-10H]
        mov     qword [rax], rdx
        cmp     qword [rbp-28H], 1
        jnz     L_010
        mov     rax, qword [rbp-8H]
        jmp     L_013

L_010:  mov     dword [rbp-14H], 1
        jmp     L_012

L_011:  mov     rax, qword [rbp-30H]
        lea     rdx, [rax-8H]
        mov     rax, qword [rbp-28H]
        sub     rax, 1
        mov     rsi, rdx
        mov     rdi, rax
        call    _lib_alloc
        mov     rcx, rax
        mov     eax, dword [rbp-14H]
        cdqe
        lea     rdx, [rax*8]
        mov     rax, qword [rbp-8H]
        add     rax, rdx
        mov     rdx, rcx
        mov     qword [rax], rdx
        add     dword [rbp-14H], 1
L_012:  mov     eax, dword [rbp-14H]
        cdqe
        cmp     qword [rbp-10H], rax
        jge     L_011
        mov     rax, qword [rbp-8H]
L_013:  leave
        ret



SECTION .data


SECTION .bss


SECTION .rodata

L_014:
        db 25H, 64H, 00H

L_015:
        db 25H, 64H, 0AH, 00H

L_016:
        db 25H, 73H, 00H

global main
global _global_x
global _global_y

SECTION .text
main:
	push	rbp
	mov	rbp, rsp
	mov	qword [rel _global_x], 10
	mov	qword [rel _global_y], 20
	mov	rbx, qword [rel _global_x]
	add	rbx, qword [rel _global_y]
	neg	rbx
	mov	rdi, rbx
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_printlnInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	pop	rbp
	ret

SECTION .data

SECTION .bss
_global_x:
	resw	8
_global_y:
	resw	8
