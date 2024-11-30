module.exports = grammar({
  name: 'project_flow_syntax',

  extras: $ => [$.whitespace],

  precedences: $ => [
    [$.tasks, $.task_split_operation, $.dependency],
  ],

  rules: {
    source: $ => choice(
      $.commands,
      $.response,
      $.interactive_session
    ),

    commands: $ => seq(
      $.command,
      repeat(seq($.newline, optional($.command)))
    ),

    interactive_session: $ => repeat1(seq(
      $.command,
      $.newline,
      $.response,
      optional($.newline)
    )),

    command: $ => choice(
      $.dependency,
      $.task_split_operation,
      $.entity_create_or_update,
      $.entity_remove,
      $.task_explode_implode,
      $.cluster_operation,
      $.comment,
      $.resource_assignment
    ),

    comment: $ => seq($.comment_sigil, $.comment_text),
    comment_text: $ => /[^\n\r]*/,

    dependency: $ => choice(
      seq($.tasks, repeat1(seq($.required_by_op, $.tasks))),
      seq($.tasks, repeat1(seq($.required_by_op, $.milestone))),
      seq($.milestone, repeat1(seq($.required_by_op, $.tasks))),
      // Delete a dependency.
      seq($.task, $.negation_op, $.required_by_op, $.tasks),
      seq($.tasks, $.negation_op, $.required_by_op, $.task),
    ),

    task_split_operation: $ => prec.left(choice(
      seq($.new_task_sigil, $.required_by_op, $.task),
      seq($.task, $.required_by_op, $.new_task_sigil)
    )),

    entity_create_or_update: $ => $.entity,

    entity_remove: $ => seq(
      $.negation_op,
      $.entity
    ),

    task_explode_implode: $ => choice(
      seq($.task, $.explode_op, $.number),
      seq($.tasks, $.implode_op, $.task)
    ),

    cluster_operation: $ => seq(
      optional($.negation_op),
      $.cluster_sigil,
      $.identifier,
      $.kv_separator,
      choice($.task, $.milestone),
      repeat(seq($.separator, choice($.task, $.milestone)))
    ),

    resource_assignment: $ => seq(
      $.tasks,
      optional($.negation_op),
      $.required_by_op,
      $.resources
    ),

    entity: $ => choice(
      $.task,
      $.milestone,
      $.resource
    ),

    tasks: $ => seq(
      $.task,
      repeat(seq($.separator, $.task))
    ),

    task: $ => seq(
      $.identifier,
      optional($.attributes),
    ),

    milestone: $ => seq(
      $.milestone_sigil,
      $.identifier,
      optional($.attributes)
    ),

    resource: $ => seq(
      $.resource_sigil,
      $.identifier,
      optional($.attributes)
    ),

    resources: $ => seq(
      $.resource,
      repeat(seq($.separator, $.resource))
    ),

    attributes: $ => seq(
      $.left_bracket,
      choice(
        $.negation_op, // Clear all attributes.
        seq($.attribute, repeat(seq($.separator, $.attribute)))
      ),
      $.right_bracket,
    ),

    attribute: $ => seq(
      $.attribute_key,
      $.kv_separator,
      choice(
        $.quoted_value,
        $.value,
        $.negation_op // Clear this attribute.
      )
    ),

    attribute_key: $ => $.identifier,

    quoted_value: $ => seq(
      $.double_quote,
      $.value_between_quotes,
      $.double_quote,
    ),

    value: $ => /[^,)"]+/,
    value_between_quotes: $ => /[^"]*/,

    response: $ => seq(
      $.number,
      optional(seq($.whitespace, /[^\n\r]*/))
    ),

    identifier: $ => /[A-Za-z][A-Za-z0-9_-]*/,

    cluster_sigil: $ => '@',
    comment_sigil: $ => '#',
    milestone_sigil: $ => '%',
    new_task_sigil: $ => '*',
    resource_sigil: $ => '$',

    explode_op: $ => '!',
    implode_op: $ => '/',
    required_by_op: $ => '>',
    negation_op: $ => '~',

    kv_separator: $ => ':',
    separator: $ => ',',

    whitespace: $ => /[ \t]+/,
    newline: $ => /[\n\r]+/,
    left_bracket: $ =>'(',
    right_bracket: $ =>')',
    double_quote: $ => '"',

    number: $ => /[0-9]+/,
  }
});
