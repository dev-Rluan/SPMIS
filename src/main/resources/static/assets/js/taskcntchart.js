
var i;
var j;
var color = new Array();
var username = new Array();
var kanbanid = new Array();
var kanbanstatus = new Array();

for(j=0; j<kanbanlist.length;j++){
  window['status'+j] = new Array();
  let color_r = Math.floor(Math.random() * 127 + 128).toString(16);
  let color_g = Math.floor(Math.random() * 127 + 128).toString(16);
  let color_b = Math.floor(Math.random() * 127 + 128).toString(16);
  color[j] =  `#${color_r+color_g+color_b}`;
}
for(kanban in kanbanlist){
    kanbanid.push(kanbanlist[kanban].kanban_id);
    kanbanstatus.push(kanbanlist[kanban].kanban_status);
}

for(task in taskcntlist) {
    for(i = 0; i<kanbanid.length; i++){
        if(taskcntlist[task].kanbanid == kanbanid[i]){
            window['status'+i].push(taskcntlist[task].counttask);
        }
    }
}
for(user in userlist){
    username.push(userlist[user].user_name);
}

const datasetvalue = []

for(i = 0; i<kanbanid.length; i++){
    datasetvalue[i] = {
    data: window['status'+i],
    label: kanbanstatus[i],
    backgroundColor: color[i]
    }
}


const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: username,
        datasets: datasetvalue
    },
    options: {
        responsive : false,
         scales: {//x,y축 설정
            yAxes: [{
                ticks: {
                    stepSize: 1, //y축 간격
                    suggestedMin: 0//y축 최소 값
                },
                gridLines: {//y축 라인 스타일 설정
                    borderDash: [2, 2],
                    borderDashOffset: 0.2
                },
                scaleLabel: {
                            display: true,
                            labelString: 'task수'
                }
            }],
            xAxes: [{//x축 설정
                barPercentage: 0.6,
                scaleLabel: {
                            display: true,
                            labelString: '사용자이메일'
                }
            }]
         }
    }
});

