import React from "react";

class TaskUpdateModal extends React.Component {
    render() {
        const showState = this.props.useState;
        const task = this.props.task;
        const udpateTask = this.props.updateTask;

        const updateText = () => {
            let newText = document.getElementById("edit-task-text");
            if(!newText.value){
                alert("New text is invalid");
                return;
            }
            udpateTask(task.id, newText.value)
            newText.value = ''
        }

        return (
            <div className="modal fade" id="update-task-modal" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden={showState}>
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Editing Task "{task.text}"</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <input type="text" id="edit-task-text" className="form-control" placeholder={task.text} />
                        </div>
                        <div className="">
                            <button type="button" className="btn btn-outline-secondary m-2" onClick={() => {updateText()}} data-bs-dismiss="modal">Save</button>
                            <button type="button" className="btn btn-outline-secondary m-2" data-bs-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

}


export default TaskUpdateModal;