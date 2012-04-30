(ns leiningen.gitify
  (:require [leiningen.core.main :refer [abort]]
            [clojure.string :refer [trim-newline]]
            [tentacles.repos :refer [create-repo]]
            [clojure.java.shell :refer [sh]]
            [clojure.pprint :refer [pprint]]))

(defn gitify
  "Create a git(hub) repository for your project.

This task will create a Github repository under your account
with the same name as the project name, run `git init`, and set
up the origin remote to point at the new Github repository. If
you pass the --init key, it'll also run `git add .`, commit, and
push. If you pass the --private option, it will try to make the
repository private. Obviously, this only works if you actually
have a Github plan allowing it.

This task requires that you have set the github.user and
github.password git config options."
  [project & args]
  (let [user (trim-newline (:out (sh "git" "config" "--get" "github.user")))
        pass (trim-newline (:out (sh "git" "config" "--get" "github.password")))
        name (:name project)
        args (set args)]
    (if (and user pass)
      (do (println "Creating a Github repository...")
          (let [response (create-repo name {:auth [user pass]
                                            :description (:description project)
                                            :homepage (:url project)
                                            :public (not (args "--private"))})]
            (if (:status response)
              (do (pprint response)
                  (println "\nCould not create the repository."))
              (do (println "Creating a git repository in this directory...")
                  (println (:out (sh "git" "init")))
                  (println "Setting up a remote, origin, to point at the new Github repo...")
                  (sh "git" "remote" "add" "origin" (str "git@github.com:" user "/" name ".git"))
                  (when (args "--init")
                    (println "Pushing first commit...")
                    (sh "git" "add" ".")
                    (println (:out (sh "git" "commit" "-m 'First commit.'")))
                    (println (:out (sh "git" "push" "origin" "master"))))))))
      (abort "Please github.user and github.password (using git"
             "config) and try again."))
    (println "Done! Code away!")))