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
extern sprintf
extern __isoc99_scanf
extern malloc
extern puts
extern fputs
extern printf
extern _IO_putc
extern stdout
SECTION .text
_lib_newline:
        mov     rsi, qword [rel stdout]
        mov     edi, 10
        jmp     _IO_putc
ALIGN   16
_lib_printInt:
        mov     rsi, rdi
        xor     eax, eax
        mov     edi, L_020
        jmp     printf
        nop
ALIGN   16
_lib_printlnInt:
        mov     rsi, rdi
        xor     eax, eax
        mov     edi, L_021
        jmp     printf
        nop
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
        mov     edi, L_022
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
        mov     edi, L_020
        xor     eax, eax
        lea     rsi, [rsp+0CH]
        mov     dword [rsp+0CH], 0
        call    __isoc99_scanf
        mov     eax, dword [rsp+0CH]
        add     rsp, 24
        ret
ALIGN   16
_lib_toString:
        push    rbp
        push    rbx
        mov     rbp, rdi
        mov     edi, 256
        sub     rsp, 8
        call    malloc
        mov     rdx, rbp
        mov     rbx, rax
        mov     rdi, rax
        mov     esi, L_020
        xor     eax, eax
        call    sprintf
        add     rsp, 8
        mov     rax, rbx
        pop     rbx
        pop     rbp
        ret
ALIGN   16
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
        push    r13
        push    r12
        mov     r12, rdi
        push    rbp
        push    rbx
        mov     ebx, edx
        sub     ebx, esi
        mov     ebp, esi
        lea     edi, [rbx+2H]
        sub     rsp, 8
        movsxd  rdi, edi
        call    malloc
        lea     edx, [rbx+1H]
        movsxd  rsi, ebp
        mov     rdi, rax
        add     rsi, r12
        mov     r13, rax
        movsxd  rdx, edx
        call    strncpy
        add     rsp, 8
        mov     rax, r13
        pop     rbx
        pop     rbp
        pop     r12
        pop     r13
        ret
ALIGN   16
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
        jmp     strcmp
        nop
ALIGN   16
_lib_alloc:
        push    r15
        push    r14
        mov     r15, rdi
        push    r13
        push    r12
        mov     r14, rsi
        push    rbp
        push    rbx
        sub     rsp, 216
        mov     rax, qword [rsi]
        mov     qword [rsp+18H], rdi
        mov     qword [rsp+20H], rsi
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+0C8H], rax
        call    malloc
        cmp     r15, 1
        mov     qword [rsp+40H], rax
        mov     qword [rax], rbx
        je      L_019
        test    rbx, rbx
        jle     L_019
        lea     r15, [r15-9H]
        lea     r13, [r14-48H]
        mov     qword [rsp+0A0H], 0
        mov     rax, r15
        mov     r15, r13
        mov     r13, rax
L_001:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-8H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+58H], rax
        call    malloc
        cmp     qword [rsp+18H], 2
        mov     rdx, rax
        mov     qword [rsp+60H], rax
        mov     qword [rdx], rbx
        je      L_018
        cmp     qword [rsp+58H], 0
        jle     L_018
        mov     qword [rsp+0A8H], 0
L_002:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-10H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+50H], rax
        call    malloc
        cmp     qword [rsp+18H], 3
        mov     rdx, rax
        mov     qword [rsp+68H], rax
        mov     qword [rdx], rbx
        je      L_017
        cmp     qword [rsp+50H], 0
        jle     L_017
        mov     qword [rsp+0B0H], 0
L_003:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-18H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+70H], rax
        call    malloc
        cmp     qword [rsp+18H], 4
        mov     rdx, rax
        mov     qword [rsp+78H], rax
        mov     qword [rdx], rbx
        je      L_016
        cmp     qword [rsp+70H], 0
        jle     L_016
        mov     qword [rsp+0B8H], 0
L_004:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-20H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+80H], rax
        call    malloc
        cmp     qword [rsp+18H], 5
        mov     rdx, rax
        mov     qword [rsp+88H], rax
        mov     qword [rdx], rbx
        je      L_015
        cmp     qword [rsp+80H], 0
        jle     L_015
        mov     qword [rsp+0C0H], 0
L_005:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-28H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+90H], rax
        call    malloc
        cmp     qword [rsp+18H], 6
        mov     rdx, rax
        mov     qword [rsp+98H], rax
        mov     qword [rdx], rbx
        je      L_014
        cmp     qword [rsp+90H], 0
        jle     L_014
        mov     qword [rsp+48H], 0
        mov     r14, r15
L_006:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-30H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+30H], rax
        call    malloc
        cmp     qword [rsp+18H], 7
        mov     rdx, rax
        mov     qword [rsp+38H], rax
        mov     qword [rdx], rbx
        je      L_012
        cmp     qword [rsp+30H], 0
        jle     L_012
        mov     qword [rsp+28H], 0
ALIGN   8
L_007:  mov     rax, qword [rsp+20H]
        mov     rax, qword [rax-38H]
        lea     rdi, [rax*8+8H]
        mov     rbx, rax
        mov     qword [rsp+10H], rax
        call    malloc
        cmp     qword [rsp+18H], 8
        mov     rdx, rax
        mov     qword [rsp+8H], rax
        mov     qword [rdx], rbx
        jz      L_011
        cmp     qword [rsp+10H], 0
        jle     L_011
        xor     r12d, r12d
ALIGN   8
L_008:  mov     rax, qword [rsp+20H]
        mov     rbx, qword [rax-40H]
        lea     rdi, [rbx*8+8H]
        call    malloc
        cmp     qword [rsp+18H], 9
        mov     rbp, rax
        mov     qword [rax], rbx
        jz      L_010
        test    rbx, rbx
        jle     L_010
        xor     r15d, r15d
ALIGN   8
L_009:  mov     rsi, r14
        mov     rdi, r13
        call    _lib_alloc
        mov     qword [rbp+r15*8+8H], rax
        lea     rax, [r15+2H]
        add     r15, 1
        cmp     rbx, rax
        jge     L_009
L_010:  mov     rax, qword [rsp+8H]
        mov     qword [rax+r12*8+8H], rbp
        lea     rax, [r12+1H]
        add     r12, 2
        cmp     qword [rsp+10H], r12
        jl      L_011
        mov     r12, rax
        jmp     L_008
ALIGN   8
L_011:  mov     rax, qword [rsp+28H]
        mov     rdx, qword [rsp+38H]
        mov     rcx, qword [rsp+8H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+30H], rax
        jl      L_012
        mov     qword [rsp+28H], rdx
        jmp     L_007
L_012:  mov     rax, qword [rsp+48H]
        mov     rdx, qword [rsp+98H]
        mov     rcx, qword [rsp+38H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+90H], rax
        jl      L_013
        mov     qword [rsp+48H], rdx
        jmp     L_006
L_013:  mov     r15, r14
L_014:  mov     rax, qword [rsp+0C0H]
        mov     rdx, qword [rsp+88H]
        mov     rcx, qword [rsp+98H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+80H], rax
        jl      L_015
        mov     qword [rsp+0C0H], rdx
        jmp     L_005
L_015:  mov     rax, qword [rsp+0B8H]
        mov     rdx, qword [rsp+78H]
        mov     rcx, qword [rsp+88H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+70H], rax
        jl      L_016
        mov     qword [rsp+0B8H], rdx
        jmp     L_004
L_016:  mov     rax, qword [rsp+0B0H]
        mov     rdx, qword [rsp+68H]
        mov     rcx, qword [rsp+78H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+50H], rax
        jl      L_017
        mov     qword [rsp+0B0H], rdx
        jmp     L_003
L_017:  mov     rax, qword [rsp+0A8H]
        mov     rdx, qword [rsp+60H]
        mov     rcx, qword [rsp+68H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+58H], rax
        jl      L_018
        mov     qword [rsp+0A8H], rdx
        jmp     L_002
L_018:  mov     rax, qword [rsp+0A0H]
        mov     rdx, qword [rsp+40H]
        mov     rcx, qword [rsp+60H]
        mov     qword [rdx+rax*8+8H], rcx
        lea     rdx, [rax+1H]
        add     rax, 2
        cmp     qword [rsp+0C8H], rax
        jl      L_019
        mov     qword [rsp+0A0H], rdx
        jmp     L_001
L_019:
        mov     rax, qword [rsp+40H]
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
SECTION .text
SECTION .rodata.str1.1 
L_020:
        db 25H, 64H, 00H
L_021:
        db 25H, 64H, 0AH, 00H
L_022:
        db 25H, 73H, 00H
global main
global _static_init
global _global_a
global _global_str
global _str_1
global _str_2
global _str_3
global _str_4
global _str_5
global _str_6
global _str_7
global _str_8
global _str_9
global _str_10

SECTION .text
main:
	push	rbp
	mov	rbp, rsp
	call	_static_init
	mov	rsi, 0
_Label_113:
	cmp	rsi, 29
	jg	_Label_26
_Label_33:
	mov	r15, 0
	mov	r12, qword [rel _global_a]
	mov	r12, qword [r12+rsi*8+8H]
	mov	rdi, qword [r12+8H]
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_toString
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r13, qword [rel _global_str]
	mov	qword [r13+rsi*8+8H], r12
	mov	r14, 0
_Label_88:
	cmp	r14, rsi
	jge	_Label_47
_Label_68:
	mov	r12, r14
	and	r12, 1
	cmp	r12, 0
	jne	_Label_53
_Label_62:
	mov	r13, r15
	mov	r12, qword [rel _global_a]
	mov	r12, qword [r12+rsi*8+8H]
	add	r13, qword [r12+8H]
	mov	r15, r13
_Label_53:
	mov	r12, r14
	and	r12, 1
	cmp	r12, 1
	jne	_Label_69
_Label_78:
	mov	r13, r15
	mov	r12, qword [rel _global_a]
	mov	r12, qword [r12+rsi*8+8H]
	add	r13, qword [r12+0F0H]
	mov	r15, r13
_Label_69:
	mov	r12, r14
	inc	r14
	jmp	_Label_88
_Label_47:
	mov	rdi, _str_1
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_2
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_4
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_5
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_6
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_7
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_8
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_9
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	rdi, _str_10
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	r12, rsi
	inc	rsi
	jmp	_Label_113
_Label_26:
	mov	rax, 0
	pop	rbp
	ret
_static_init:
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	qword [rbp-8H], 30
	mov	qword [rbp-10H], 30
	lea	rax, [rbp-8H]
	mov	rsi, rax
	mov	rdi, 2
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_alloc
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	qword [rel _global_a], rax
	mov	qword [rbp-18H], 30
	lea	rax, [rbp-18H]
	mov	rsi, rax
	mov	rdi, 1
	push	rcx
	push	rdx
	push	rsi
	push	rdi
	call	_lib_alloc
	pop	rdi
	pop	rsi
	pop	rdx
	pop	rcx
	mov	qword [rel _global_str], rax
	add	rsp, 32
	pop	rbp
	ret

SECTION .data
_str_1:
	db	73H, 74H, 72H, 31H, 00H
_str_2:
	db	73H, 74H, 72H, 32H, 00H
_str_3:
	db	73H, 74H, 72H, 33H, 00H
_str_4:
	db	73H, 74H, 72H, 34H, 00H
_str_5:
	db	73H, 74H, 72H, 35H, 00H
_str_6:
	db	73H, 74H, 72H, 36H, 00H
_str_7:
	db	73H, 74H, 72H, 37H, 00H
_str_8:
	db	73H, 74H, 72H, 38H, 00H
_str_9:
	db	73H, 74H, 72H, 39H, 00H
_str_10:
	db	73H, 74H, 72H, 31H, 30H, 00H

SECTION .bss
_global_a:
	resw	8
_global_str:
	resw	8
