import React from "react";

class Folders extends React.Component {
    render() {
        const folders = this.props.folders;
        const setActualTask = this.props.setActualTask;
        const setActualFolderName = this.props.setActualFolderName;
        const createFolder = this.props.createFolder;
        const deleteFolder = this.props.deleteFolder;

        const createAndValidateFolder = () => {
            let folderName = document.getElementById('new-folder-name');
            if(!folderName.value){
                alert("Invalid name");
                return;
            }
            createFolder(folderName.value);
            folderName.value = ''
        }
        const onViewItems = (folder) => {
            setActualTask(folder.tasks)
            setActualFolderName(folder.name)
        }

        return (
            <div className="col-lg-6 col-12 mt-4">
                <h2>Folders</h2>
                <ul className="list-group">
                    {
                        folders.map((folder, i) => (
                            <div key={folder.id}>
                                <li className="list-group-item border-0">
                                    {"  -  " + folder.name}
                                    <a className="m-2" onClick={() => {onViewItems(folder)}} role="button">View Items</a>
                                    <a className="m-2" onClick={() => {deleteFolder(folder)}} role="button">Remove</a>
                                </li>
                            </div>

                        ))
                    }
                </ul>

                <div className="input-group mb-3 mt-3 w-75">
                    <input type="text" id="new-folder-name" className="form-control" placeholder="New folder" aria-label="New folder"/>
                    <div className="input-group-append">
                        <button className ="btn btn-outline-secondary" onClick={() => {createAndValidateFolder()}} type ="button">Add</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Folders;