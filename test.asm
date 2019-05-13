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

global _func_hilo
global _func_shift_l
global _func_shift_r
global _func_xorshift
global _func_int2chr
global _func_toStringHex
global _func_getnumber
global main
global _static_init
global _str_1
global _global_asciiTable
global _str_2
global _str_3

SECTION .text
_func_hilo:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r12, rdi
	mov	r14, rsi
	mov	r13, r12
	sal	r13, 16
	mov	r12, r14
	or	r12, r13
	mov	rax, r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_shift_l:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r12, rdi
	mov	r13, rsi
	mov	rcx, r13
	sal	r12, cl
	mov	rsi, 65535
	mov	rdi, 32767
	call	_func_hilo
	mov	r13, rax
	and	r12, r13
	mov	rax, r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_shift_r:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r15, rsi
	mov	rsi, 65535
	mov	rdi, 32767
	call	_func_hilo
	mov	r12, rax
	mov	r14, r12
	mov	r12, r14
	mov	rcx, r15
	sar	r12, cl
	sal	r12, 1
	add	r12, 1
	mov	r14, r12
	mov	r12, r13
	mov	rcx, r15
	sar	r12, cl
	and	r14, r12
	mov	rsi, 65535
	mov	rdi, 32767
	call	_func_hilo
	mov	r13, rax
	mov	r12, r14
	and	r12, r13
	mov	rax, r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_xorshift:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r12, rdi
	mov	qword [rbp-8H], rsi
	add	r12, 1
	mov	r14, r12
	mov	r15, 0
_Label_125:
	mov	r12, qword [rbp-8H]
	mov	r13, 10
	mov	rax, r12
	cqo
	imul	r13
	mov	r12, rax
	cmp	r15, r12
	jge	_Label_90
_Label_103:
	mov	rsi, 13
	mov	rdi, r14
	call	_func_shift_l
	mov	r12, rax
	xor	r14, r12
	mov	rsi, 17
	mov	rdi, r14
	call	_func_shift_r
	mov	r12, rax
	xor	r14, r12
	mov	rsi, 5
	mov	rdi, r14
	call	_func_shift_l
	mov	r12, rax
	xor	r14, r12
	inc	r15
	jmp	_Label_125
_Label_90:
	mov	rbx, r14
	xor	rbx, 123456789
	mov	rax, rbx
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_int2chr:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r12, rdi
	cmp	r12, 32
	jl	_Label_135
_Label_150:
	cmp	r12, 126
	jg	_Label_135
_Label_146:
	mov	r13, r12
	sub	r13, 32
	sub	r12, 32
	mov	rdx, r12
	mov	rsi, r13
	mov	rdi, qword [rel _global_asciiTable]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_str_substring
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rbx, rax
	mov	rax, rbx
_Label_132:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_135:
	mov	rax, _str_2
	jmp	_Label_132
_func_toStringHex:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	qword [rbp-8H], rdi
	mov	r14, _str_2
	mov	r15, 28
_Label_209:
	cmp	r15, 0
	jl	_Label_166
_Label_180:
	mov	r12, qword [rbp-8H]
	mov	rcx, r15
	sar	r12, cl
	and	r12, 15
	mov	r13, r12
	cmp	r13, 10
	jge	_Label_192
_Label_201:
	mov	r12, 48
	add	r12, r13
	mov	rdi, r12
	call	_func_int2chr
	mov	r12, rax
	mov	rdi, r14
	mov	rsi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_strcat
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r14, r12
_Label_181:
	sub	r15, 4
	jmp	_Label_209
_Label_192:
	mov	r12, 65
	add	r12, r13
	sub	r12, 10
	mov	rdi, r12
	call	_func_int2chr
	mov	r12, rax
	mov	rdi, r14
	mov	rsi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_strcat
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r14, r12
	jmp	_Label_181
_Label_166:
	mov	rax, r14
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_getnumber:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r14, rdi
	mov	r13, rsi
	mov	r12, rdx
	and	r12, 31
	mov	r15, r12
	mov	rsi, r13
	mov	rdi, r14
	call	_func_xorshift
	mov	r12, rax
	mov	r13, r12
	mov	rsi, r15
	mov	rdi, r13
	call	_func_shift_l
	mov	r14, rax
	mov	r12, 32
	sub	r12, r15
	mov	rsi, r12
	mov	rdi, r13
	call	_func_shift_r
	mov	r13, rax
	mov	r12, r14
	or	r12, r13
	mov	rax, r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
main:
	push	rbp
	mov	rbp, rsp
	sub	rsp, 256
	call	_static_init
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rbx, rax
	mov	qword [rbp-8H], rbx
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-10H], r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-18H], r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-20H], r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-28H], r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-30H], r12
	mov	qword [rbp-38H], 30
	mov	qword [rbp-40H], 0
	mov	qword [rbp-48H], 0
	mov	qword [rbp-50H], 0
	mov	qword [rbp-58H], 0
	mov	r10, qword [rbp-8H]
	mov	qword [rbp-60H], r10
_Label_409:
	mov	r11, qword [rbp-10H]
	cmp	qword [rbp-60H], r11
	jge	_Label_295
_Label_301:
	mov	r10, qword [rbp-18H]
	mov	qword [rbp-68H], r10
_Label_405:
	mov	r11, qword [rbp-20H]
	cmp	qword [rbp-68H], r11
	jge	_Label_303
_Label_309:
	mov	r10, qword [rbp-28H]
	mov	qword [rbp-70H], r10
_Label_401:
	mov	r11, qword [rbp-30H]
	cmp	qword [rbp-70H], r11
	jge	_Label_311
_Label_322:
	mov	rdx, qword [rbp-70H]
	mov	rsi, qword [rbp-38H]
	mov	rdi, qword [rbp-8H]
	call	_func_getnumber
	mov	r12, rax
	mov	r15, r12
	mov	rdx, qword [rbp-70H]
	mov	rsi, qword [rbp-38H]
	mov	rdi, qword [rbp-60H]
	call	_func_getnumber
	mov	r12, rax
	mov	qword [rbp-78H], r12
	mov	rdx, qword [rbp-70H]
	mov	rsi, qword [rbp-38H]
	mov	rdi, qword [rbp-68H]
	call	_func_getnumber
	mov	r12, rax
	mov	qword [rbp-80H], r12
	mov	r12, qword [rbp-60H]
	xor	r12, qword [rbp-68H]
	mov	rdx, qword [rbp-70H]
	mov	rsi, qword [rbp-38H]
	mov	rdi, r12
	call	_func_getnumber
	mov	r12, rax
	mov	qword [rbp-88H], r12
	mov	rsi, 1
	mov	rdi, qword [rbp-70H]
	call	_func_xorshift
	mov	r13, rax
	mov	rsi, 1
	mov	rdi, qword [rbp-68H]
	call	_func_xorshift
	mov	r12, rax
	mov	r14, r13
	xor	r14, r12
	mov	rsi, 1
	mov	rdi, qword [rbp-60H]
	call	_func_xorshift
	mov	r13, rax
	mov	r12, r14
	xor	r12, r13
	mov	r13, r12
	mov	r12, r15
	xor	r12, r13
	mov	rsi, 1
	mov	rdi, r12
	call	_func_xorshift
	mov	r12, rax
	add	qword [rbp-40H], r12
	mov	r12, qword [rbp-78H]
	xor	r12, r13
	mov	rsi, 1
	mov	rdi, r12
	call	_func_xorshift
	mov	r12, rax
	add	qword [rbp-48H], r12
	mov	r12, qword [rbp-80H]
	xor	r12, r13
	mov	rsi, 1
	mov	rdi, r12
	call	_func_xorshift
	mov	r12, rax
	add	qword [rbp-50H], r12
	mov	r12, qword [rbp-88H]
	xor	r12, r13
	mov	rsi, 1
	mov	rdi, r12
	call	_func_xorshift
	mov	r12, rax
	add	qword [rbp-58H], r12
	inc	qword [rbp-70H]
	jmp	_Label_401
_Label_311:
	inc	qword [rbp-68H]
	jmp	_Label_405
_Label_303:
	inc	qword [rbp-60H]
	jmp	_Label_409
_Label_295:
	mov	rdi, qword [rbp-40H]
	call	_func_toStringHex
	mov	r12, rax
	mov	rdi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, qword [rbp-48H]
	call	_func_toStringHex
	mov	r12, rax
	mov	rdi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, qword [rbp-50H]
	call	_func_toStringHex
	mov	r12, rax
	mov	rdi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, qword [rbp-58H]
	call	_func_toStringHex
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
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_2
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	r10
	push	r11
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	r11
	pop	r10
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rax, 0
	add	rsp, 256
	pop	rbp
	ret
_static_init:
	push	rbp
	mov	rbp, rsp
	mov	rbx, _str_1
	mov	qword [rel _global_asciiTable], rbx
	pop	rbp
	ret

SECTION .data
_str_1:
	db	20H, 21H, 22H, 23H, 24H, 25H, 26H, 27H, 28H, 29H, 2AH, 2BH, 2CH, 2DH, 2EH, 2FH, 30H, 31H, 32H, 33H, 34H, 35H, 36H, 37H, 38H, 39H, 3AH, 3BH, 3CH, 3DH, 3EH, 3FH, 40H, 41H, 42H, 43H, 44H, 45H, 46H, 47H, 48H, 49H, 4AH, 4BH, 4CH, 4DH, 4EH, 4FH, 50H, 51H, 52H, 53H, 54H, 55H, 56H, 57H, 58H, 59H, 5AH, 5BH, 5CH, 5DH, 5EH, 5FH, 60H, 61H, 62H, 63H, 64H, 65H, 66H, 67H, 68H, 69H, 6AH, 6BH, 6CH, 6DH, 6EH, 6FH, 70H, 71H, 72H, 73H, 74H, 75H, 76H, 77H, 78H, 79H, 7AH, 7BH, 7CH, 7DH, 7EH, 00H
_str_2:
	db	00H
_str_3:
	db	20H, 00H

SECTION .bss
_global_asciiTable:
	resw	8
