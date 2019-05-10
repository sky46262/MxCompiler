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
global _func_max
global _class_Node.copy
global _class_Node.init
global _class_Node.judge
global _class_Node.push_up
global _class_Node.addtag_ch
global _class_Node.addtag_ro
global _class_Node.push_down
global _class_Node.rot
global _class_Node.rotto
global _class_splay_tree._init
global _class_splay_tree.build
global _class_splay_tree.build_tree
global _class_splay_tree.find
global _class_splay_tree.dfs
global _class_splay_tree.del
global _class_splay_tree.change
global _class_splay_tree.rol
global _class_splay_tree.getsum
global _class_splay_tree.getMax
global _func_equ
global _func_merge
global main
global _static_init
global _global_INF
global _global_nMax
global _global_n
global _global_m
global _global_id_cnt
global _global_din
global _global_sp
global _global_dintree
global _str_1
global _global_char_ID
global _str_2
global _str_3

SECTION .text
_func_max:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r12, rsi
	cmp	r13, r12
	jle	_Label_49
_Label_51:
	mov	rax, r13
_Label_44:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_49:
	mov	rax, r12
	jmp	_Label_44
_class_Node.copy:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r14, rsi
	mov	r12, qword [r14]
	mov	qword [r15], r12
	mov	r12, qword [r14+8H]
	mov	qword [r15+8H], r12
	mov	r12, qword [r14+10H]
	mov	qword [r15+10H], r12
	mov	r12, qword [r14+18H]
	mov	qword [r15+18H], r12
	mov	r12, qword [r14+20H]
	mov	qword [r15+20H], r12
	mov	r12, qword [r14+28H]
	mov	qword [r15+28H], r12
	mov	r12, qword [r14+30H]
	mov	qword [r15+30H], r12
	mov	r12, qword [r14+38H]
	mov	qword [r15+38H], r12
	mov	r12, qword [r14+40H]
	mov	qword [r15+40H], r12
	mov	r12, qword [r14+48H]
	mov	qword [r15+48H], r12
	mov	r12, qword [r14+50H]
	mov	r13, qword [r12+8H]
	mov	r12, qword [r15+50H]
	mov	qword [r12+8H], r13
	mov	r12, qword [r14+50H]
	mov	r12, qword [r12+10H]
	mov	r13, qword [r15+50H]
	mov	qword [r13+10H], r12
	mov	r12, qword [r14+58H]
	mov	qword [r15+58H], r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.init:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r14, rdi
	mov	r12, rsi
	mov	r13, rdx
	mov	qword [r14], r13
	mov	qword [r14+10H], 1
	mov	qword [r14+20H], 0
	mov	qword [r14+30H], 0
	mov	qword [r14+28H], 0
	mov	qword [r14+18H], r12
	mov	qword [r14+8H], r12
	mov	qword [r14+38H], r12
	mov	qword [r14+40H], r12
	mov	qword [r14+48H], r12
	mov	qword [rbp-8H], 2
	lea	rax, [rbp-8H]
	mov	rsi, rax
	mov	rdi, 1
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_alloc
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	qword [r14+50H], rax
	mov	r12, qword [r14+50H]
	mov	qword [r12+8H], 0
	mov	r12, qword [r14+50H]
	mov	qword [r12+10H], 0
	mov	qword [r14+58H], 0
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.judge:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r12, rsi
	mov	rsi, r12
	mov	r12, qword [r13+50H]
	mov	rdi, qword [r12+8H]
	call	_func_equ
	mov	rbx, rax
	cmp	rbx, 1
	jne	_Label_202
_Label_204:
	mov	rax, 0
_Label_197:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_202:
	mov	rax, 1
	jmp	_Label_197
_class_Node.push_up:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r15, rdi
	mov	qword [r15+10H], 1
	mov	r12, qword [r15+8H]
	mov	qword [r15+18H], r12
	mov	r14, 0
_Label_255:
	cmp	r14, 2
	jge	_Label_223
_Label_251:
	mov	r12, qword [r15+50H]
	cmp	qword [r12+r14*8+8H], 0
	je	_Label_229
_Label_237:
	mov	r13, qword [r15+10H]
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+r14*8+8H]
	add	r13, qword [r12+10H]
	mov	qword [r15+10H], r13
	mov	r13, qword [r15+18H]
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+r14*8+8H]
	add	r13, qword [r12+18H]
	mov	qword [r15+18H], r13
_Label_229:
	inc	r14
	jmp	_Label_255
_Label_223:
	mov	r12, qword [r15+8H]
	mov	qword [r15+38H], r12
	mov	r12, qword [r15+8H]
	mov	qword [r15+40H], r12
	mov	r12, qword [r15+8H]
	mov	qword [r15+48H], r12
	mov	r14, qword [r15+8H]
	mov	r10, qword [r15+8H]
	mov	qword [rbp-8H], r10
	mov	r12, qword [r15+50H]
	cmp	qword [r12+8H], 0
	je	_Label_269
_Label_275:
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+8H]
	mov	r12, qword [r12+38H]
	mov	qword [r15+38H], r12
	mov	r13, r14
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+8H]
	add	r13, qword [r12+18H]
	mov	r14, r13
	mov	rsi, 0
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+8H]
	mov	rdi, qword [r12+40H]
	call	_func_max
	mov	r12, rax
	add	qword [rbp-8H], r12
_Label_269:
	mov	r12, qword [r15+50H]
	cmp	qword [r12+10H], 0
	je	_Label_301
_Label_307:
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+10H]
	mov	r12, qword [r12+40H]
	mov	qword [r15+40H], r12
	mov	r13, qword [rbp-8H]
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+10H]
	add	r13, qword [r12+18H]
	mov	qword [rbp-8H], r13
	mov	rsi, 0
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+10H]
	mov	rdi, qword [r12+38H]
	call	_func_max
	mov	r12, rax
	add	r14, r12
_Label_301:
	mov	rsi, r14
	mov	rdi, qword [r15+38H]
	call	_func_max
	mov	r12, rax
	mov	qword [r15+38H], r12
	mov	rsi, qword [rbp-8H]
	mov	rdi, qword [r15+40H]
	call	_func_max
	mov	r12, rax
	mov	qword [r15+40H], r12
	mov	r14, 0
_Label_372:
	cmp	r14, 2
	jge	_Label_347
_Label_368:
	mov	r12, qword [r15+50H]
	cmp	qword [r12+r14*8+8H], 0
	je	_Label_353
_Label_362:
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+r14*8+8H]
	mov	rsi, qword [r12+48H]
	mov	rdi, qword [r15+48H]
	call	_func_max
	mov	r12, rax
	mov	qword [r15+48H], r12
_Label_353:
	inc	r14
	jmp	_Label_372
_Label_347:
	mov	r13, qword [r15+8H]
	mov	r12, qword [r15+50H]
	cmp	qword [r12+8H], 0
	je	_Label_375
_Label_386:
	mov	rsi, 0
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+8H]
	mov	rdi, qword [r12+40H]
	call	_func_max
	mov	r12, rax
	add	r13, r12
_Label_375:
	mov	r12, qword [r15+50H]
	cmp	qword [r12+10H], 0
	je	_Label_393
_Label_404:
	mov	rsi, 0
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+10H]
	mov	rdi, qword [r12+38H]
	call	_func_max
	mov	r12, rax
	add	r13, r12
_Label_393:
	mov	rsi, r13
	mov	rdi, qword [r15+48H]
	call	_func_max
	mov	r12, rax
	mov	qword [r15+48H], r12
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.addtag_ch:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r14, rsi
	mov	qword [r13+8H], r14
	mov	r12, qword [r13+10H]
	mov	rax, r12
	cqo
	imul	r14
	mov	r12, rax
	mov	qword [r13+18H], r12
	cmp	r14, 0
	jl	_Label_431
_Label_440:
	mov	r12, qword [r13+18H]
	mov	qword [r13+38H], r12
	mov	r12, qword [r13+18H]
	mov	qword [r13+40H], r12
	mov	r12, qword [r13+18H]
	mov	qword [r13+48H], r12
_Label_428:
	mov	qword [r13+20H], 1
	mov	qword [r13+28H], r14
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_431:
	mov	qword [r13+38H], r14
	mov	qword [r13+40H], r14
	mov	qword [r13+48H], r14
	jmp	_Label_428
_class_Node.addtag_ro:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r12, qword [r15+50H]
	mov	r14, qword [r12+8H]
	mov	r12, qword [r15+50H]
	mov	r13, qword [r12+10H]
	mov	r12, qword [r15+50H]
	mov	qword [r12+8H], r13
	mov	r12, qword [r15+50H]
	mov	qword [r12+10H], r14
	mov	r12, qword [r15+38H]
	mov	r13, qword [r15+40H]
	mov	qword [r15+38H], r13
	mov	qword [r15+40H], r12
	mov	r12, qword [r15+30H]
	xor	r12, 1
	mov	qword [r15+30H], r12
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.push_down:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r14, rdi
	cmp	qword [r14+20H], 1
	jne	_Label_496
_Label_497:
	mov	r13, 0
_Label_522:
	cmp	r13, 2
	jge	_Label_499
_Label_518:
	mov	r12, qword [r14+50H]
	cmp	qword [r12+r13*8+8H], 0
	je	_Label_505
_Label_512:
	mov	rsi, qword [r14+28H]
	mov	r12, qword [r14+50H]
	mov	rdi, qword [r12+r13*8+8H]
	call	_class_Node.addtag_ch
	mov	r12, rax
_Label_505:
	inc	r13
	jmp	_Label_522
_Label_499:
	mov	qword [r14+20H], 0
_Label_496:
	cmp	qword [r14+30H], 1
	jne	_Label_530
_Label_531:
	mov	r13, 0
_Label_555:
	cmp	r13, 2
	jge	_Label_533
_Label_551:
	mov	r12, qword [r14+50H]
	cmp	qword [r12+r13*8+8H], 0
	je	_Label_539
_Label_545:
	mov	r12, qword [r14+50H]
	mov	rdi, qword [r12+r13*8+8H]
	call	_class_Node.addtag_ro
	mov	r12, rax
_Label_539:
	inc	r13
	jmp	_Label_555
_Label_533:
	mov	qword [r14+30H], 0
_Label_530:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.rot:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	qword [rbp-8H], rdi
	mov	r11, qword [rbp-8H]
	mov	r15, qword [r11+58H]
	mov	rsi, qword [rbp-8H]
	mov	rdi, r15
	call	_class_Node.judge
	mov	r12, rax
	mov	r14, r12
	mov	r12, r14
	xor	r12, 1
	mov	r10, qword [rbp-8H]
	mov	r13, qword [r10+50H]
	mov	r13, qword [r13+r12*8+8H]
	mov	r12, qword [r15+50H]
	mov	qword [r12+r14*8+8H], r13
	mov	r12, r14
	xor	r12, 1
	mov	r11, qword [rbp-8H]
	mov	r13, qword [r11+50H]
	mov	qword [r13+r12*8+8H], r15
	mov	r12, qword [r15+50H]
	cmp	qword [r12+r14*8+8H], 0
	je	_Label_589
_Label_596:
	mov	r12, qword [r15+50H]
	mov	r12, qword [r12+r14*8+8H]
	mov	qword [r12+58H], r15
_Label_589:
	mov	r12, qword [r15+58H]
	mov	r10, qword [rbp-8H]
	mov	qword [r10+58H], r12
	mov	r11, qword [rbp-8H]
	mov	qword [r15+58H], r11
	mov	r10, qword [rbp-8H]
	cmp	qword [r10+58H], 0
	je	_Label_612
_Label_622:
	mov	rsi, r15
	mov	r11, qword [rbp-8H]
	mov	rdi, qword [r11+58H]
	call	_class_Node.judge
	mov	r13, rax
	mov	r10, qword [rbp-8H]
	mov	r12, qword [r10+58H]
	mov	r12, qword [r12+50H]
	mov	r11, qword [rbp-8H]
	mov	qword [r12+r13*8+8H], r11
_Label_612:
	mov	rdi, r15
	call	_class_Node.push_up
	mov	rbx, rax
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_Node.rotto:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r15, rdi
	mov	qword [rbp-8H], rsi
_Label_651:
	mov	rsi, qword [rbp-8H]
	mov	rdi, qword [r15+58H]
	call	_func_equ
	mov	r12, rax
	cmp	r12, 1
	jne	_Label_640
_Label_636:
	mov	rdi, r15
	call	_class_Node.push_up
	mov	r12, rax
	cmp	qword [r15+58H], 0
	je	_Label_682
_Label_686:
	mov	rdi, qword [r15+58H]
	call	_class_Node.push_up
	mov	rbx, rax
_Label_682:
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_640:
	mov	r14, qword [r15+58H]
	mov	rsi, qword [rbp-8H]
	mov	rdi, qword [r14+58H]
	call	_func_equ
	mov	r12, rax
	cmp	r12, 1
	jne	_Label_641
_Label_643:
	mov	rdi, r15
	call	_class_Node.rot
	mov	r12, rax
	jmp	_Label_651
_Label_641:
	mov	rsi, r14
	mov	rdi, qword [r14+58H]
	call	_class_Node.judge
	mov	r13, rax
	mov	rsi, r15
	mov	rdi, r14
	call	_class_Node.judge
	mov	r12, rax
	cmp	r13, r12
	jne	_Label_653
_Label_659:
	mov	rdi, r14
	call	_class_Node.rot
	mov	r12, rax
	mov	rdi, r15
	call	_class_Node.rot
	mov	r12, rax
	jmp	_Label_651
_Label_653:
	mov	rdi, r15
	call	_class_Node.rot
	mov	r12, rax
	mov	rdi, r15
	call	_class_Node.rot
	mov	r12, rax
	jmp	_Label_651
_class_splay_tree._init:
	push	rbp
	mov	rbp, rsp
	mov	r12, rdi
	mov	qword [r12], 0
	pop	rbp
	ret
_class_splay_tree.build:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 64
	mov	qword [rbp-8H], rdi
	mov	qword [rbp-10H], rsi
	mov	r14, rdx
	mov	r15, rcx
	mov	qword [rbp-18H], r8
	mov	r12, r15
	add	r12, qword [rbp-18H]
	sar	r12, 1
	mov	qword [rbp-20H], r12
	mov	rdi, 96
	call	malloc
	mov	r13, rax
	inc	qword [rel _global_id_cnt]
	mov	rdx, qword [rel _global_id_cnt]
	mov	r12, qword [rel _global_din]
	mov	r10, qword [rbp-20H]
	mov	rsi, qword [r12+r10*8+8H]
	mov	rdi, r13
	call	_class_Node.init
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, qword [rbp-10H]
	call	_class_Node.copy
	mov	r12, rax
	mov	r11, qword [rbp-10H]
	mov	qword [r11+58H], r14
	cmp	r15, qword [rbp-20H]
	jge	_Label_735
_Label_741:
	mov	rdi, 96
	call	malloc
	mov	r10, qword [rbp-10H]
	mov	r12, qword [r10+50H]
	mov	qword [r12+8H], rax
	inc	qword [rel _global_id_cnt]
	mov	rdx, qword [rel _global_id_cnt]
	mov	rsi, 0
	mov	r11, qword [rbp-10H]
	mov	r12, qword [r11+50H]
	mov	rdi, qword [r12+8H]
	call	_class_Node.init
	mov	r12, rax
	mov	r12, qword [rbp-20H]
	sub	r12, 1
	mov	r8, r12
	mov	rcx, r15
	mov	rdx, qword [rbp-10H]
	mov	r10, qword [rbp-10H]
	mov	r12, qword [r10+50H]
	mov	rsi, qword [r12+8H]
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.build
	mov	r12, rax
_Label_735:
	mov	r11, qword [rbp-20H]
	cmp	qword [rbp-18H], r11
	jle	_Label_767
_Label_773:
	mov	rdi, 96
	call	malloc
	mov	r10, qword [rbp-10H]
	mov	r12, qword [r10+50H]
	mov	qword [r12+10H], rax
	inc	qword [rel _global_id_cnt]
	mov	rdx, qword [rel _global_id_cnt]
	mov	rsi, 0
	mov	r11, qword [rbp-10H]
	mov	r12, qword [r11+50H]
	mov	rdi, qword [r12+10H]
	call	_class_Node.init
	mov	r12, rax
	mov	r12, qword [rbp-20H]
	add	r12, 1
	mov	r8, qword [rbp-18H]
	mov	rcx, r12
	mov	rdx, qword [rbp-10H]
	mov	r10, qword [rbp-10H]
	mov	r12, qword [r10+50H]
	mov	rsi, qword [r12+10H]
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.build
	mov	r12, rax
_Label_767:
	mov	rdi, qword [rbp-10H]
	call	_class_Node.push_up
	mov	rbx, rax
	add	rsp, 64
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.build_tree:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r14, rsi
	mov	r15, rdx
	mov	rdi, 96
	call	malloc
	mov	qword [r13], rax
	inc	qword [rel _global_id_cnt]
	mov	rdx, qword [rel _global_id_cnt]
	mov	rsi, 0
	mov	rdi, qword [r13]
	call	_class_Node.init
	mov	r12, rax
	mov	r8, r15
	mov	rcx, r14
	mov	rdx, 0
	mov	rsi, qword [r13]
	mov	rdi, r13
	call	_class_splay_tree.build
	mov	rbx, rax
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.find:
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
	mov	r14, qword [r12]
	mov	r15, 0
	mov	r12, qword [r14+50H]
	cmp	qword [r12+8H], 0
	je	_Label_836
_Label_845:
	mov	r12, qword [r14+50H]
	mov	r12, qword [r12+8H]
	mov	r12, qword [r12+10H]
	add	r12, 1
	mov	r13, r12
_Label_883:
	mov	r12, r15
	add	r12, r13
	cmp	r12, qword [rbp-8H]
	je	_Label_853
_Label_878:
	mov	r12, r15
	add	r12, r13
	cmp	qword [rbp-8H], r12
	jge	_Label_860
_Label_872:
	mov	r12, qword [r14+50H]
	mov	r14, qword [r12+8H]
_Label_855:
	mov	rdi, r14
	call	_class_Node.push_down
	mov	r12, rax
	mov	r12, qword [r14+50H]
	cmp	qword [r12+8H], 0
	je	_Label_886
_Label_895:
	mov	r12, qword [r14+50H]
	mov	r12, qword [r12+8H]
	mov	r12, qword [r12+10H]
	add	r12, 1
	mov	r13, r12
	jmp	_Label_883
_Label_886:
	mov	r13, 1
	jmp	_Label_883
_Label_860:
	add	r15, r13
	mov	r12, qword [r14+50H]
	mov	r14, qword [r12+10H]
	jmp	_Label_855
_Label_853:
	mov	rax, r14
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_836:
	mov	r13, 1
	jmp	_Label_883
_class_splay_tree.dfs:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r14, rsi
	mov	r13, 0
_Label_941:
	cmp	r13, 2
	jge	_Label_918
_Label_937:
	mov	r12, qword [r14+50H]
	cmp	qword [r12+r13*8+8H], 0
	je	_Label_924
_Label_930:
	mov	r12, qword [r14+50H]
	mov	rsi, qword [r12+r13*8+8H]
	mov	rdi, r15
	call	_class_splay_tree.dfs
	mov	r12, rax
_Label_924:
	mov	r12, r13
	inc	r13
	jmp	_Label_941
_Label_918:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.del:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r14, rsi
	mov	r12, rdx
	add	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r13
	mov	r12, r14
	sub	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	rsi, qword [r15]
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	r12, qword [r13+50H]
	cmp	qword [r12+10H], 0
	je	_Label_976
_Label_982:
	mov	r12, qword [r13+50H]
	mov	rsi, qword [r12+10H]
	mov	rdi, r15
	call	_class_splay_tree.dfs
	mov	r12, rax
_Label_976:
	mov	r12, qword [r13+50H]
	mov	qword [r12+10H], 0
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r13
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.change:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	qword [rbp-8H], rdi
	mov	r14, rsi
	mov	r12, rdx
	mov	r15, rcx
	add	r12, 1
	mov	rsi, r12
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	r11, qword [rbp-8H]
	mov	qword [r11], r13
	mov	r12, r14
	sub	r12, 1
	mov	rsi, r12
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	r10, qword [rbp-8H]
	mov	rsi, qword [r10]
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	r12, qword [r13+50H]
	mov	r13, qword [r12+10H]
	mov	rdi, r13
	call	_class_Node.push_down
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r13
	call	_class_Node.addtag_ch
	mov	r12, rax
	mov	rdi, r13
	call	_class_Node.push_down
	mov	r12, rax
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	r11, qword [rbp-8H]
	mov	qword [r11], r13
	add	rsp, 32
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.rol:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r14, rsi
	mov	r12, rdx
	add	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r13
	mov	r12, r14
	sub	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r13, r12
	mov	rsi, qword [r15]
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	r12, qword [r13+50H]
	mov	r13, qword [r12+10H]
	mov	rdi, r13
	call	_class_Node.push_down
	mov	r12, rax
	mov	rdi, r13
	call	_class_Node.addtag_ro
	mov	r12, rax
	mov	rdi, r13
	call	_class_Node.push_down
	mov	r12, rax
	mov	rsi, 0
	mov	rdi, r13
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r13
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.getsum:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r15, rdi
	mov	r13, rsi
	mov	r12, rdx
	add	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r14, r12
	mov	rsi, 0
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r14
	mov	r12, r13
	sub	r12, 1
	mov	rsi, r12
	mov	rdi, r15
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r14, r12
	mov	rsi, qword [r15]
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	r12, qword [r14+50H]
	mov	r14, qword [r12+10H]
	mov	rdi, r14
	call	_class_Node.push_down
	mov	r12, rax
	mov	r13, qword [r14+18H]
	mov	rsi, 0
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	qword [r15], r14
	mov	rax, r13
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_class_splay_tree.getMax:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	rdi, qword [r13]
	call	_class_Node.push_down
	mov	r12, rax
	mov	rbx, qword [r13]
	mov	rax, qword [rbx+48H]
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_func_equ:
	push	rbp
	mov	rbp, rsp
	mov	r13, rdi
	mov	r12, rsi
	cmp	r13, 0
	jne	_Label_1197
_Label_1204:
	cmp	r12, 0
	jne	_Label_1198
_Label_1200:
	mov	rax, 1
_Label_1218:
	pop	rbp
	ret
_Label_1198:
	mov	rax, 0
	jmp	_Label_1218
_Label_1197:
	cmp	r12, 0
	jne	_Label_1211
_Label_1213:
	mov	rax, 0
	jmp	_Label_1218
_Label_1211:
	mov	r12, qword [r12]
	cmp	qword [r13], r12
	jne	_Label_1220
_Label_1219:
	mov	rax, 1
	jmp	_Label_1218
_Label_1220:
	mov	rax, 0
	jmp	_Label_1218
_func_merge:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r13, rdi
	mov	qword [rbp-8H], rsi
	mov	r15, rdx
	mov	r12, r13
	add	r12, 1
	mov	rsi, r12
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r14, r12
	mov	rsi, 0
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	r10, qword [rbp-8H]
	mov	qword [r10], r14
	mov	rsi, r13
	mov	rdi, qword [rbp-8H]
	call	_class_splay_tree.find
	mov	r12, rax
	mov	r14, r12
	mov	r11, qword [rbp-8H]
	mov	rsi, qword [r11]
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	r13, qword [r15]
	mov	r12, qword [r14+50H]
	mov	qword [r12+10H], r13
	mov	r12, qword [r15]
	mov	qword [r12+58H], r14
	mov	rsi, 0
	mov	rdi, r14
	call	_class_Node.rotto
	mov	r12, rax
	mov	r10, qword [rbp-8H]
	mov	qword [r10], r14
	add	rsp, 32
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
	sub	rsp, 64
	call	_static_init
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rel _global_n], r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rel _global_m], r12
	mov	rdi, 8
	call	malloc
	mov	r12, rax
	mov	rdi, rax
	call	_class_splay_tree._init
	mov	qword [rel _global_sp], r12
	mov	rdi, 8
	call	malloc
	mov	r12, rax
	mov	rdi, rax
	call	_class_splay_tree._init
	mov	qword [rel _global_dintree], r12
	mov	r12, qword [rel _global_INF]
	neg	r12
	mov	r13, qword [rel _global_din]
	mov	qword [r13+8H], r12
	mov	r13, qword [rel _global_n]
	add	r13, 1
	mov	r12, qword [rel _global_INF]
	neg	r12
	mov	r14, qword [rel _global_din]
	mov	qword [r14+r13*8+8H], r12
	mov	qword [rbp-8H], 1
_Label_1339:
	mov	r11, qword [rel _global_n]
	cmp	qword [rbp-8H], r11
	jg	_Label_1324
_Label_1335:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r13, qword [rel _global_din]
	mov	r10, qword [rbp-8H]
	mov	qword [r13+r10*8+8H], r12
	mov	r12, qword [rbp-8H]
	inc	qword [rbp-8H]
	jmp	_Label_1339
_Label_1324:
	mov	r12, qword [rel _global_n]
	add	r12, 1
	mov	rdx, r12
	mov	rsi, 0
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.build_tree
	mov	r12, rax
	mov	qword [rbp-8H], 1
_Label_1591:
	mov	r11, qword [rel _global_m]
	cmp	qword [rbp-8H], r11
	jg	_Label_1352
_Label_1359:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getString
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	qword [rbp-10H], r12
	mov	rsi, 0
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r13, rax
	mov	rsi, 0
	mov	rdi, qword [rel _global_char_ID]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r13, r12
	jne	_Label_1364
_Label_1366:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
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
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r15, r12
	mov	r14, 1
_Label_1396:
	cmp	r14, r15
	jg	_Label_1381
_Label_1392:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r13, qword [rel _global_din]
	mov	qword [r13+r14*8+8H], r12
	inc	r14
	jmp	_Label_1396
_Label_1381:
	mov	rdx, r15
	mov	rsi, 1
	mov	rdi, qword [rel _global_dintree]
	call	_class_splay_tree.build_tree
	mov	r12, rax
	mov	r12, qword [rbp-20H]
	add	r12, 1
	mov	rdx, qword [rel _global_dintree]
	mov	rsi, qword [rel _global_sp]
	mov	rdi, r12
	call	_func_merge
	mov	r12, rax
_Label_1364:
	mov	rsi, 0
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r13, rax
	mov	rsi, 1
	mov	rdi, qword [rel _global_char_ID]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r13, r12
	jne	_Label_1422
_Label_1424:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r15, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r14, r12
	mov	r13, r15
	add	r13, 1
	mov	r12, r15
	add	r12, r14
	mov	rdx, r12
	mov	rsi, r13
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.del
	mov	r12, rax
_Label_1422:
	mov	rsi, 0
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r12, 82
	jne	_Label_1457
_Label_1459:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r15, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r14, r12
	mov	r13, r15
	add	r13, 1
	mov	r12, r15
	add	r12, r14
	mov	rdx, r12
	mov	rsi, r13
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.rol
	mov	r12, rax
_Label_1457:
	mov	rsi, 0
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r12, 71
	jne	_Label_1501
_Label_1490:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r15, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r13, r12
	cmp	r13, 0
	jle	_Label_1504
_Label_1518:
	mov	r14, r15
	add	r14, 1
	mov	r12, r15
	add	r12, r13
	mov	rdx, r12
	mov	rsi, r14
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.getsum
	mov	r12, rax
	mov	rdi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_printlnInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
_Label_1501:
	mov	rsi, 0
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r12, 77
	jne	_Label_1532
_Label_1579:
	mov	rsi, 2
	mov	rdi, qword [rbp-10H]
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_ord
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	cmp	r12, 75
	jne	_Label_1542
_Label_1544:
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r15, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
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
	push	rsi
	push	rdi
	call	_lib_getInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r14, r12
	mov	r13, r15
	add	r13, 1
	mov	r12, r15
	add	r12, qword [rbp-18H]
	mov	rcx, r14
	mov	rdx, r12
	mov	rsi, r13
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.change
	mov	r12, rax
_Label_1532:
	inc	qword [rbp-8H]
	jmp	_Label_1591
_Label_1542:
	mov	rdi, qword [rel _global_sp]
	call	_class_splay_tree.getMax
	mov	r12, rax
	mov	rdi, r12
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_printInt
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rdi, _str_3
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	jmp	_Label_1532
_Label_1504:
	mov	rdi, _str_2
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_print
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	jmp	_Label_1501
_Label_1352:
	mov	rax, 0
	add	rsp, 64
	pop	rbp
	ret
_static_init:
	push	rbp
	mov	rbp, rsp
	sub	rsp, 32
	mov	r10, qword [rel _global_nMax]
	mov	qword [rbp-8H], r10
	lea	rax, [rbp-8H]
	mov	rsi, rax
	mov	rdi, 1
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_alloc
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	qword [rel _global_din], rax
	mov	rbx, _str_1
	mov	qword [rel _global_char_ID], rbx
	add	rsp, 32
	pop	rbp
	ret

SECTION .data
_global_INF:
	dq	1000000000
_global_nMax:
	dq	4000010
_global_id_cnt:
	dq	0
_str_1:
	db	49H, 44H, 00H
_str_2:
	db	30H, 0AH, 00H
_str_3:
	db	0AH, 00H

SECTION .bss
_global_n:
	resw	8
_global_m:
	resw	8
_global_din:
	resw	8
_global_sp:
	resw	8
_global_dintree:
	resw	8
_global_char_ID:
	resw	8
