package com.company.frontend.AST;

public enum ASTNodeType{
    cu_root,

    //statement
    s_block,s_if,s_for,s_while,
    s_return,s_continue,s_break,
    s_vardecl,s_classdecl,s_funcdecl,
    s_paramlist,s_empty,

    //expression
    e_member,e_idx,e_call,
    e_add,e_sub,e_pos,e_neg,e_mult,e_div,e_mod,
    e_inc_p,e_inc_s,e_dec_p,e_dec_s,
    e_bneg,e_band,e_bor,e_bxor,
    e_shl,e_shr,
    e_not,e_land,e_lor, //logical
    e_eq,e_ne,e_le,e_ge,e_gt,e_lt,
    e_asgn, e_creator,
    e_list,e_empty,

    //primary
    p_int,p_bool,p_str,p_null,p_this,
    p_id,

    //type
    t_void,t_int,t_bool,t_str,t_class
}
