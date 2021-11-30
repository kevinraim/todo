import { useState, useEffect } from 'react';
import './assets/css/App.css';
import Folders from './components/Folders.js';
import Task from "./components/Task.js"
import TaskUpdateModal from './components/TaskUpdateModal.js';
import AuthenticationModal from './components/AuthenticationModal.js';

function App() {

  const [showAuthenticationModal, setShowAuthenticationModal] = useState(true);
  const [folders, setFolders] = useState([]);
  const [actualTask, setActualTask] = useState([]);
  const [actualFolderName, setActualFolderName] = useState()
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [taskToEdit, setTaskToEdit] = useState([]);
  const [authenticationToken, setAuthenticationToken] = useState()

  const path = "http://localhost:8080/";

  const login = async (email, password) => {
    let requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email, password:password })
    };
    let response = await fetch(path + "/auth/log_in", requestOptions);
    let responseJSON = await response.json()
    if(responseJSON.message === "Bad credentials"){
      alert("Invalid email or password")
      return;
    }
    setAuthenticationToken(responseJSON.jwtToken);
    setShowAuthenticationModal("")
  }

  const register = async (email, password) => {
    let requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email, password:password })
    };
    let response = await fetch(path + "/auth/sign_up", requestOptions);
    let responseJSON = await response.json()
    if(responseJSON.message === "Email alredy taken"){
      alert("The email is alredy taken")
      return;
    }
    if(responseJSON.message === "Invalid email"){
      alert("Invalid email")
      return;
    }
    setAuthenticationToken(responseJSON.jwtToken);
    setShowAuthenticationModal("")
  }

  const getFolders = async () => {
    let requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json',
      Authorization : "Bearer " + authenticationToken},
    };
    let response = await fetch(path + "folders", requestOptions)
    let responseJSON = await response.json();
    setFolders(responseJSON.folders)
  };

  const createFolder = async (name) => {
    let requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 
      Authorization : "Bearer " + authenticationToken},
      body: JSON.stringify({ name: name })
    };
    let response = await fetch(path + "/folders", requestOptions)
    if(response.status === 400){
      alert("The folder is alredy created. Please choose anothe name.");
      return;
    }
    let newFolder = await response.json();
    setFolders([...folders, newFolder])
    setActualFolderName(newFolder.name)
    setActualTask([])
  }

  const deleteFolder = async(folder) => {
    let requestOptions = {
      method: 'DELETE',
      headers: {Authorization : "Bearer " + authenticationToken}
    };
    await fetch(path + "/folders/" + folder.id, requestOptions)
    console.log(folder)
    setFolders(folders.filter(item => item.id !== folder.id))
    if(folder.name === actualFolderName){
      setActualFolderName();
      setActualTask([]);
    }
  }

  const createTask = async(text) => {
    let requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json',
      Authorization : "Bearer " + authenticationToken},
      body: JSON.stringify({ text: text, folderName: actualFolderName})
    };
    let response = await fetch(path + "/tasks", requestOptions)
    let newTask = await response.json()
    setActualTask([...actualTask, newTask])
  }

  const updateTaskIsDone =  async (id, isDone) => {
    let requestOptions = {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json',
      Authorization : "Bearer " + authenticationToken},
      body: JSON.stringify({isDone: isDone})
    };
    await fetch(path + "/tasks/" + id, requestOptions)
    let updateTask = [...actualTask]
    updateTask.forEach(element => {
      if(element.id === id){
        element.isDone = isDone;
      }
    });
    setActualTask(updateTask);
  }

  const updateTaskText = async(id, text) => {
    let requestOptions = {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json',
      Authorization : "Bearer " + authenticationToken},
      body: JSON.stringify({text: text})
    };
    await fetch(path + "/tasks/" + id, requestOptions)
    let updatedTask = [...actualTask]
    updatedTask.forEach(element => {
      if(element.id === id){
        element.text = text;
      }
    });
    setActualTask(updatedTask);
    setShowUpdateModal(false);
  } 

  useEffect(() => {
    if(authenticationToken)
      getFolders()
  }, [authenticationToken, actualTask])

  return (
    <div className="container">
      <div className="row">
        <AuthenticationModal showAuthenticationModal={showAuthenticationModal} login={login} register={register} />
        <Folders folders={folders} setActualTask={setActualTask} setActualFolderName={setActualFolderName} createFolder={createFolder} deleteFolder={deleteFolder}/>
        <Task tasks={actualTask} folderName={actualFolderName} createTask={createTask} updateTaskIsDone={updateTaskIsDone} setTaskToEdit={setTaskToEdit} setShowUpdateModal={setShowUpdateModal}/>
        <TaskUpdateModal showState={showUpdateModal} task={taskToEdit} updateTask={updateTaskText}/>
      </div>
    </div>
  );
}

export default App;
