default rel






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

extern strcmp
extern strcat
extern strcpy
extern atol
extern strncpy
extern strlen
extern sprintf
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
        lea     rdi, [rel L_006]
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
        lea     rdi, [rel L_007]
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
        lea     rdi, [rel L_008]
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
        lea     rdi, [rel L_006]
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
        sub     rsp, 32
        mov     qword [rbp-18H], rdi
        mov     edi, 256
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rdx, qword [rbp-18H]
        mov     rax, qword [rbp-8H]
        lea     rsi, [rel L_006]
        mov     rdi, rax
        mov     eax, 0
        call    sprintf
        mov     rax, qword [rbp-8H]
        leave
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
        jnz     L_002
        mov     rax, qword [rbp-8H]
        jmp     L_005

L_002:  mov     dword [rbp-14H], 1
        jmp     L_004

L_003:  mov     rax, qword [rbp-30H]
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
L_004:  mov     eax, dword [rbp-14H]
        cdqe
        cmp     qword [rbp-10H], rax
        jge     L_003
        mov     rax, qword [rbp-8H]
L_005:  leave
        ret


SECTION .data


SECTION .bss


SECTION .rodata

L_006:
        db 25H, 64H, 00H

L_007:
        db 25H, 64H, 0AH, 00H

L_008:
        db 25H, 73H, 00H

global _func_a
global main

SECTION .text
_func_a:
	push	rbp
	mov	rbp, rsp
	sub	rsp, 128
	mov	r12, rdi
	mov	qword [rbp-8H], rsi
	mov	qword [rbp-10H], rdx
	mov	qword [rbp-18H], rcx
	mov	r14, r8
	mov	r15, r9
	mov	r13, qword [rbp+10H]
	add	r12, qword [rbp-8H]
	add	r12, qword [rbp-10H]
	add	r12, qword [rbp-18H]
	add	r12, r14
	add	r12, r15
	add	r12, r13
	add	r12, qword [rbp+18H]
	add	r12, qword [rbp+20H]
	add	r12, qword [rbp+28H]
	add	r12, qword [rbp+30H]
	add	r12, qword [rbp+38H]
	add	r12, qword [rbp+40H]
	add	r12, qword [rbp+48H]
	add	r12, qword [rbp+50H]
	mov	rax, r12
	add	rsp, 128
	pop	rbp
	ret
main:
	push	rbp
	mov	rbp, rsp
	push	15
	push	14
	push	13
	push	12
	push	11
	push	10
	push	9
	push	8
	push	7
	mov	r9, 6
	mov	r8, 5
	mov	rcx, 4
	mov	rdx, 3
	mov	rsi, 2
	mov	rdi, 1
	call	_func_a
	add	rsp, 72
	mov	rbx, rax
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
	mov	rax, 0
	pop	rbp
	ret

SECTION .data

SECTION .bss
