$('#modal_add_task').on('shown.bs.modal', function (event) { 
  var e = $(event.relatedTarget);    
  $('#kanban_id').val(e.data('kanbanid')) ;
    
});

$('#modal_edit_task').on('shown.bs.modal', function (event) { 
    var e = $(event.relatedTarget);    
    $('#task_name').html(e.data('task_name')) ;
    $('#create_nick').html(e.data('create_nick')) ;
    $('#create_time').html(e.data('create_time')) ;
    $('#content').html(e.data('content')) ;
    $('#start_nick').html(e.data('start_nick')) ;
    $('#start_user_email').html(e.data('start_user_email')) ;
    $('#taskid').val(e.data('task_id')) ;
    
});

$('#modal_edit_group').on('shown.bs.modal', function (event) { 
  var e = $(event.relatedTarget);    
  $('#edit_user_con').html(e.data('user_email')) ;    $('#edit_user').html(e.data('user_email')) ;
    $('#role').val(e.data('role')) ;
    
});

$('#modal_del_group').on('shown.bs.modal', function (event) { 
  var e = $(event.relatedTarget);    
  $('#del_user').val(e.data('user_email')) ;
    $('#del_user_con').html(e.data('user_email')) ;
    
});

$('#modal_edit_status').on('shown.bs.modal', function (event) { 
  var e = $(event.relatedTarget);    
  $('#task_id').val(e.data('taskid')) ;
    $('#project_id').val(e.data('projectid')) ;
    
});



// $('#edit_task').on('click', function (event) { 
//     event.preventDefault();
//     $('#modal_edit_task').modal('show').find('.modal-body').load($(this).attr('href'));   
    
// });
