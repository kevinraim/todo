import React from "react";

class Task extends React.Component {
    render() {

        const tasks = this.props.tasks;
        const folderName = this.props.folderName;
        const createTask = this.props.createTask;
        const updateTaskIsDone = this.props.updateTaskIsDone;
        const setShowUpdateModal = this.props.setShowUpdateModal;
        const setTaskToEdit = this.props.setTaskToEdit;

        const isAddTaskEnable = () => {
            if (folderName) {
                return false;
            }
            return true;
        }

        const createAndValidateTask = () => {
            let text = document.getElementById('new-task-text');
            if(!text.value){
                alert("Invalid text");
                return;
            }
            createTask(text.value);
            text.value = ''
        }
        const editTask = (task) => {
            setShowUpdateModal(true); 
            setTaskToEdit(task);
        }

        return (
            <div className="col-lg-6 col-12 mt-4">
                <h2>Folder {'>'} {folderName}</h2>
                <ul className="list-group">
                    {
                        tasks.map((task, i) => (
                            <div key={i}>
                                <li className="list-group-item border-0">
                                    <input className="form-check-input" onChange={() => {updateTaskIsDone(task.id, !task.isDone)}} type="checkbox" value="" id="flexCheckDefault" checked={task.isDone ? true : false} />
                                    {"  -  " + task.text}
                                    <a className="m-2" role="button" onClick={() => {editTask(task)}} data-bs-toggle="modal" data-bs-target="#update-task-modal">Edit</a>
                                </li>
                            </div>

                        ))
                    }
                </ul>

                <div className="input-group mb-3 mt-3 w-75">
                    <input type="text" id="new-task-text" className="form-control" placeholder="New task"/>
                    <div className="input-group-append">
                        <button className="btn btn-outline-secondary" onClick={() => { createAndValidateTask() }} type="button" disabled={isAddTaskEnable()}>Add</button>
                    </div>
                </div>
            </div>
        );
    }
}


export default Task;